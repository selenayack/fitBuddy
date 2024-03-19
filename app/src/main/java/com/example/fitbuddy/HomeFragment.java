package com.example.fitbuddy;

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
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        DbAdapter db = new DbAdapter(requireContext()) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };
        db.open();


        //db.delete("food_diary_kalori_yenen", null, null);
      //db.delete("food_diary", null, null);




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

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String stringFdDate  = df.format(Calendar.getInstance().getTime());


        String stringFdDateSql = db.quoteSmart(stringFdDate);

        updateTableItems(stringFdDateSql,"0");
        updateTableItems(stringFdDateSql,"1");
        updateTableItems(stringFdDateSql,"2");
        updateTableItems(stringFdDateSql,"3");


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



        FloatingActionButton floatingActionButtonOgleYemegi=view.findViewById(R.id.ÖğleYemeğiAdd);
        if (floatingActionButtonOgleYemegi != null) {
            floatingActionButtonOgleYemegi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    addFood(1);
                }
            });
        } else {

        }
        FloatingActionButton floatingActionButtonAkşam=view.findViewById(R.id.Akşam_YemeğiAdd);
        if (floatingActionButtonAkşam != null) {
            floatingActionButtonAkşam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    addFood(2);
                }
            });
        } else {

        }
        FloatingActionButton floatingActionButtonAtıstırmalik=view.findViewById(R.id.AtıştırmalıkAdd);
        if (floatingActionButtonAtıstırmalik != null) {
            floatingActionButtonAtıstırmalik.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    addFood(3);
                }
            });
        } else {

        }












    }


    private void updateTableItems(String stringFdDate, String mealNumber) {


        DbAdapter db= new DbAdapter(requireContext()) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };
        db.open();
        String stringMealNumberSQL=db.quoteSmart(mealNumber);





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

        String fdwhereClause[] = new String[]{
                "fd_tarih",
                "fd_ögün_numara"
        };

        String fdwhereCondition[]=new String[]{
                stringDateSQL,
                stringMealNumberSQL
        };
        String fdwhereAndOr[]=new String[]{
                "AND"
        };







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

        Cursor cursorFdYenenkalori;
        String fieldsYenenKalori[]=new String[]{
                "_id",
                "kalori_yenen_id",
                "kalori_yenen_tarih ",
                "kalori_yenen_ogun_no ",
                "kalori_yenen_kalori",
                "kalori_yenen_protein",
                "kalori_yenen_karbonhidrat",
                "kalori_yenen_yag"


        };

        String whereClause[] = new String[]{
                "kalori_yenen_tarih",
                "kalori_yenen_ogun_no"
        };

        String whereCondition[]=new String[]{
             stringDateSQL,
                stringMealNumberSQL
        };
        String whereAndOr[]=new String[]{
            "AND"
        };




        cursorFdYenenkalori = db.select("food_diary_kalori_yenen",fieldsYenenKalori,whereClause,whereCondition,whereAndOr);
        //Cursor cursorFdYenenkalorii = db.select("food_diary_kalori_yenen",fieldsYenenKalori);



        int cursorFdYenenCount=cursorFdYenenkalori.getCount();

        if(cursorFdYenenCount==0) {


            String insFields = "_id, kalori_yenen_tarih, kalori_yenen_ogun_no, kalori_yenen_kalori, kalori_yenen_protein ,kalori_yenen_karbonhidrat, kalori_yenen_yag";
            String insValues = "NULL," + stringFdDate + ","+ stringMealNumberSQL + ", '0', '0', '0', '0'";

            db.insert("food_diary_kalori_yenen", insFields, insValues);

            //cursorFdYenenkalori = db.select("food_diary_kalori_yenen",fieldsYenenKalori);
            cursorFdYenenkalori = db.select("food_diary_kalori_yenen",fieldsYenenKalori);

        }




        int intFdceYenenKalori=0;
        int intFdceYenenProtein=0;
        int intFdceYenenKarb=0;
        int intFdceYenenYag=0;


       int intCursorCount=cursorFd.getCount();


       if (cursorFd != null && cursorFd.moveToFirst()) {
          for(int x=0;x<intCursorCount;x++){
               String stringFdId = cursorFd.getString(0);


               String fdFoodId = cursorFd.getString(1); // 1. sütundaki değeri al
               String fdFoodIdSQL = db.quoteSmart(fdFoodId);

               String fdServingSizeGram=cursorFd.getString(2);
               String fdServingSizeGramOlcu=cursorFd.getString(3);
               String fdServingSizeAdet=cursorFd.getString(4);
               String fdServingSizeAdetOlcu=cursorFd.getString(5);
               String fdFoodEnergy=cursorFd.getString(6);
               String fdFoodProtein=cursorFd.getString(7);
               String fdFoodKarb=cursorFd.getString(8);
               String fdFoodYag=cursorFd.getString(9);
               int intfdFoodEnergy=Integer.parseInt(fdFoodEnergy);
               int intfdFoodProtein=Integer.parseInt(fdFoodProtein);
               int intfdFoodKarb=Integer.parseInt(fdFoodKarb);
               int intfdFoodYag=Integer.parseInt(fdFoodYag);




              cursorFood = db.select("food", fieldsFood,"_id",fdFoodId);





               if (cursorFood != null && cursorFood.moveToFirst()) {
                   String foodName = cursorFood.getString(1); // 1. sütundaki değeri al

                   String foodId=cursorFood.getString(0);
                   String subLine=fdServingSizeGram+" "+fdServingSizeGramOlcu+","+fdServingSizeAdet+" "+fdServingSizeAdetOlcu;
                   TableLayout tl=null;

                   if(mealNumber=="0"){
                       Log.d("MealNumber", "Kahvaltı seçildi");
                       tl=view.findViewById(R.id.tableLayoutKahvaltıItems);

                   }
                   else if(mealNumber=="1"){
                       Log.d("MealNumber", "Ogle seçildi");
                       tl=view.findViewById(R.id.tableLayoutOgleItems);


                   }
                   else if(mealNumber=="2"){
                       Log.d("MealNumber", "aksam seçildi");
                       tl=view.findViewById(R.id.tableLayoutAksamItems);


                   }

                   else if(mealNumber=="3"){
                       Log.d("MealNumber", " seçildi atıstırmalık");
                       tl=view.findViewById(R.id.tableLayoutAtıstırmalıkItems);


                   }




                   TableRow tr=new TableRow(getActivity());
                   tr.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));


                   TableRow tr2=new TableRow(getActivity());
                   tr2.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));

                   TextView textViewName=new TextView(getActivity());
                   textViewName.setText(foodName);


                   TextView textViewKaloriItems=new TextView(getActivity());
                   textViewKaloriItems.setText(fdFoodEnergy);


                   tr.addView(textViewName);

                   TextView textViewEmpty = new TextView(getActivity());
                   textViewEmpty.setText("");
                   tr.addView(textViewEmpty);

                   tr.addView(textViewKaloriItems);

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
               intFdceYenenKalori= intFdceYenenKalori+intfdFoodEnergy;
               intFdceYenenProtein= intFdceYenenProtein+intfdFoodProtein;
               intFdceYenenKarb= intFdceYenenKarb+intfdFoodKarb;
               intFdceYenenYag=  intFdceYenenYag+intfdFoodYag;

               TextView textViewKalori=null;
               if(mealNumber=="0"){
                   textViewKalori=view.findViewById(R.id.textViewKahvaltıKalori);
                   textViewKalori.setText(String.valueOf(intFdceYenenKalori));

               }
               else if(mealNumber=="1"){
                   textViewKalori=view.findViewById(R.id.textViewOgleKalori);
                   textViewKalori.setText(String.valueOf(intFdceYenenKalori));



               }
               else if(mealNumber=="2"){
                   textViewKalori=view.findViewById(R.id.textViewKAksamKalori);
                   textViewKalori.setText(String.valueOf(intFdceYenenKalori));


               }

               else if(mealNumber=="3"){
                   textViewKalori=view.findViewById(R.id.textViewAtıstırmalıkKalori);
                   textViewKalori.setText(String.valueOf(intFdceYenenKalori));



               }

              String updateFields[]=new String[]{
                      "kalori_yenen_kalori",
                      "kalori_yenen_protein",
                      "kalori_yenen_karbonhidrat",
                      "kalori_yenen_yag"

              };
              String updateValues[]=new String[]{
                      "'"+intFdceYenenKalori+"'",
                      "'"+intFdceYenenProtein+"'",
                      "'"+intFdceYenenKarb+"'",
                      "'"+intFdceYenenYag+"'"

              };
              //  System.out.println("kalori"+intFdceYenenKalori);




              if (cursorFdYenenkalori != null && cursorFdYenenkalori.moveToFirst()) {
                  String stringFdceId = cursorFdYenenkalori.getString(0);

                  long  longFdceId = Long.parseLong(stringFdceId);

                  db.update("food_diary_kalori_yenen","_id",longFdceId,updateFields,updateValues);
              }
              else{
                  System.out.println("cursor boşşş");
              }







              cursorFd.moveToNext();
           }





       }


       else {

       }

       db.close();



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

