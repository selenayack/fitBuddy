package com.example.fitbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

public class HomeFragment extends Fragment {

    Cursor addFoodCursor;
    private View view;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    private  HedefFragment hedefFragment = new HedefFragment();
    private  KategoriFragment kategoriFragment = new KategoriFragment();
    private YemekFragment yemekFragment = new YemekFragment();

    private String currentId;
    private String currentName;

    private String currentDateYıl="";
    private String currentDateAy="";
    private String currentDateGün="";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        view = inflater.inflate(R.layout.fragment_home, container, false);


        /*Bundle bundle=this.getArguments();
        if(bundle!=null){
            Toast.makeText(getActivity(),"Bundle:"+bundle.toString(),Toast.LENGTH_LONG).show();
        }*/

        drawerLayout = view.findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        Toolbar toolbar = view.findViewById(R.id.toolBar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
                NavigationView navigationView = view.findViewById(R.id.nav_view);
                setupDrawerContent(navigationView);
            }
        });



        if (isDatabaseLoaded()==false) {

            // Veritabanı yüklenmemişse yükleme işlemini gerçekleştir
            databaseLoad();
            setDatabaseLoaded();
        }

        LinearLayout linearLayout1 = view.findViewById(R.id.linearlayout);

        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), user_infos.class);
                startActivity(intent);
            }
        });

        /*Bundle args = getArguments();
        if (args != null) {
            int mealNumber = args.getInt("mealNumber", 0); // mealNumber'ı alın
            // mealNumber'ı kullanın
        }*/

       /*FloatingActionButton buttonAddKahvaltı=view.findViewById(R.id.KahvatıAddd);

        buttonAddKahvaltı.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment yeniFragment = new AddFoodToDiaryFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, yeniFragment);
                transaction.addToBackStack(null); // Geri butonu ile geri dönülebilirlik ekler
                transaction.commit();


            }
        });*/
        FloatingActionButton buttonAddOgleYemegi=view.findViewById(R.id.ÖğleYemeğiAdd);

        buttonAddOgleYemegi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment yeniFragment = new AddFoodToDiaryFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, yeniFragment);
                transaction.addToBackStack(null); // Geri butonu ile geri dönülebilirlik ekler
                transaction.commit();



            }
        });
        FloatingActionButton buttonAddAksamYemegi=view.findViewById(R.id.Akşam_YemeğiAdd);

        buttonAddAksamYemegi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment yeniFragment = new AddFoodToDiaryFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, yeniFragment);
                transaction.addToBackStack(null); // Geri butonu ile geri dönülebilirlik ekler
                transaction.commit();



            }
        });
        FloatingActionButton buttonAddAraOgun=view.findViewById(R.id.AtıştırmalıkAdd);

        buttonAddAraOgun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment yeniFragment = new AddFoodToDiaryFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, yeniFragment);
                transaction.addToBackStack(null); // Geri butonu ile geri dönülebilirlik ekler
                transaction.commit();



            }
        });

        initalizeHome();



        return view;
    }




    private static final String PREFS_NAME = "FitBuddy15";
    private static final String DATABASE_LOADED_KEY = "database_load15";

    private void initalizeHome() {

        DbAdapter db = new DbAdapter(requireContext()) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };
        db.open();

     if(currentDateYıl.equals("")|| currentDateAy.equals("")||currentDateGün.equals("")){

          Calendar calendar=Calendar.getInstance();
          int yıl=calendar.get(Calendar.YEAR);
          int ay=calendar.get(Calendar.MONTH);
          int gün=calendar.get(Calendar.DAY_OF_YEAR);

          currentDateGün=""+gün;
          currentDateAy=""+ ay;
          currentDateYıl=""+yıl;
    }

      String stringFdDate=currentDateGün+"-"+currentDateAy+"-"+currentDateYıl;
      String stringFdDateSQL=db.quoteSmart(stringFdDate);



      FloatingActionButton floatingActionButtonKahvaltı=view.findViewById(R.id.KahvatıAddd);
        if (floatingActionButtonKahvaltı != null) {
            floatingActionButtonKahvaltı.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    addFood(0);
                }
            });
        } else {

        }

        updateTable(stringFdDate,"0");





    }

   private void updateTable(String stringFdDate, String s) {

        DbAdapter db= new DbAdapter(requireContext()) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };
        db.open();



        String fields[]=new String[]{
                "_id",
                "fd_besin_id",
                "fd_porsiyon_büyüklügü_gram ",
                "fd_porsiyon_büyüklügü_ölcüsü_gram",
                "fd_porsiyon_büyüklügü_adet ",
                "fd_porsiyon_büyüklügü_ölcüsü_adet ",
                "fd_kalori_hesaplanmıs",
                "fd_protein_hesaplanmıs",
                "fd_karbonhidrat_hesaplanmıs",
                "fd_yag_hesaplanmıs"

        };

        String stringDateSQL=db.quoteSmart(stringFdDate);


       Cursor cursorFd = db.select("food_diary", fields);
       String fieldsFood[]=new String[]{
               "_id",
               "besin_isim",
               "besin_porsiyon_büyüklügü_gram",
               "besin_porsiyon_büyüklügü_ölcüsü_gram",
               "besin_porsiyon_büyüklügü_adet",
               "besin_porsiyon_büyüklügü_adet_ölcüsü",
               "besin_kalori "


       };
       Cursor cursorFood;
       int intCursorCount=cursorFd.getCount();


       if (cursorFd != null && cursorFd.moveToFirst()) {
           do {
               String stringFdId = cursorFd.getString(0);


               String fdFoodId = cursorFd.getString(1); // 1. sütundaki değeri al
               String fdFoodIdSQL = db.quoteSmart(fdFoodId);

               String fdServingSizeGram=cursorFd.getString(2);
               String fdServingSizeGramOlcu=cursorFd.getString(3);
               String fdServingSizeAdet=cursorFd.getString(4);
               String fdServingSizeAdetOlcu=cursorFd.getString(5);
               String fdFoodEnergy=cursorFd.getString(6);



               cursorFood = db.select("food", fieldsFood,"_id",fdFoodId);
               if (cursorFood != null && cursorFood.moveToFirst()) {
                   String foodName = cursorFood.getString(1); // 1. sütundaki değeri al

                   String foodId=cursorFood.getString(0);
                   String subLine=fdServingSizeGram+" "+fdServingSizeGramOlcu+","+fdServingSizeAdet+" "+fdServingSizeAdetOlcu;

                   TableLayout tl=(TableLayout)view.findViewById(R.id.tableLayoutKahvaltıItems);
                   TableRow tr=new TableRow(getActivity());
                   tr.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));


                   TableRow tr2=new TableRow(getActivity());
                   tr2.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));

                   TextView textViewName=new TextView(getActivity());
                   textViewName.setText(foodName);


                   TextView textViewKalori=new TextView(getActivity());
                   textViewKalori.setText(fdFoodEnergy);


                   tr.addView(textViewName);

                   TextView textViewEmpty = new TextView(getActivity());
                   textViewEmpty.setText("");
                   tr.addView(textViewEmpty);

                   tr.addView(textViewKalori);

                   TextView textViewInfo=new TextView(getActivity());
                   textViewInfo.setText(subLine);
                   tr2.addView(textViewInfo);

                   tl.addView(tr,new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));

                   tl.addView(tr2,new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));


                  /* TextView textViewKahvaltıItemsName = view.findViewById(R.id.textViewKahvaltııtemsName);
                   textViewKahvaltıItemsName.setText(foodName);
                   TextView textViewKahvaltıItemSub = view.findViewById(R.id.textViewKahvaltııtemsToplam);
                   textViewKahvaltıItemSub .setText(subLine);
                   TextView textViewKahvaltıItemKalori = view.findViewById(R.id.textViewKahvaltıItemsKalori);
                   textViewKahvaltıItemKalori .setText(fdFoodEnergy);*/
               }

           } while (cursorFd.moveToNext());
       } else {
           // Cursor boşsa veya veri yoksa bu durumu işleyin
       }











   }

    private void addFood(int mealNumber) {



        Fragment fragment = new AddFoodToDiaryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("mealNumber", mealNumber); // Anahtar olarak "mealNumber"ı ekleyin
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();




    }







    private boolean isDatabaseLoaded() {
        Context context = requireContext();
        String dbPath = context.getDatabasePath("fitBuddyDiet.db").getAbsolutePath();

        // Veritabanı dosyasının varlığını ve içeriğini kontrol et
        DbAdapter db = new DbAdapter(context) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };
        db.open();
        int satirSayisi = db.count("food");


        if (satirSayisi > 0) {
            Log.d("VeritabanıDurumu", "Veritabanı yüklü");
            return true;
        } else {
            Log.d("VeritabanıDurumu", "Veritabanı yüklü değil");
            return false;
        }
    }


    private void setDatabaseLoaded() {
        requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(DATABASE_LOADED_KEY, true)
                .apply();
    }

    private void databaseLoad() {

        Context context = getContext(); // Context'i uygun şekilde alın
        String dbPath = context.getDatabasePath("fitBuddyDiet.db").getAbsolutePath();
        Log.d("DatabasePath", "Veritabanı Yolu: " + dbPath);
        DbAdapter db = new DbAdapter(requireContext()) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };
        db.open();

        int rows=db.count("food");
        System.out.println("rows "+rows);


        Toast.makeText(requireContext(), "Yükleniyor", Toast.LENGTH_SHORT).show();
        DBSetupInsert setupInsert=new DBSetupInsert(requireContext());
        setupInsert.insertAllFood();
        setupInsert.insertAllCategories();
        Toast.makeText(requireContext(), "Yüklendi", Toast.LENGTH_SHORT).show();




        //long id=1;
        //String value="slny_ackgoz@hotmail.com";
        //db.update("USER","user_id",id,"user_email",value);



       // db.close();

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        // Menü öğesine tıklandığında ilgili fragmenti aç
                        selectDrawerItem(item);
                        drawerLayout.closeDrawers(); // Drawer'ı kapat
                        return true;
                    }
                });
    }




    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        int itemId = menuItem.getItemId();

        if (itemId == R.id.hedef) {
            fragment = hedefFragment;
        } else if (itemId == R.id.kategori) {
            fragment = kategoriFragment;
        } else if (itemId == R.id.yemek) {
            fragment = yemekFragment;
        }

        if (fragment != null) {
            setFragment(fragment);
            setTitle(menuItem.getTitle());
        }

        menuItem.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START); // Drawer'ı kapat
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void setFragment(Fragment fragment) {
        if (getActivity() != null) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    private void setTitle(CharSequence title) {
        if (getActivity() != null) {
            getActivity().setTitle(title);
        }
    }


}

