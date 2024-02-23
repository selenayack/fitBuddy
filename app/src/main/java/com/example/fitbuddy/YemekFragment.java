package com.example.fitbuddy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class YemekFragment extends Fragment {

    private View mainView;
    private Cursor listCursor;

    private MenuItem menuItemEdit;
    private  MenuItem menuItemDelete;

    private String currentId;
    private String currentName;

    public YemekFragment(){
        
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //noinspection deprecation
        populateListFood();
        //noinspection deprecation
        setHasOptionsMenu(true);



    }
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            mainView = inflater.inflate(R.layout.fragment_yemek, container, false);
            ((MainActivity2) getActivity()).getSupportActionBar().setTitle("Besinler");

            return mainView;
        }

        private void setMainView(int id){
        LayoutInflater inflater=(LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mainView=inflater.inflate(id,null);
        ViewGroup rootView=(ViewGroup) getView();
        rootView.removeAllViews();
        rootView.addView(mainView);

        }

       /** @noinspection deprecation*/
       public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){


            ((MainActivity2)getActivity()).getMenuInflater().inflate(R.menu.categories_menu,menu);
            menuItemEdit=menu.findItem((R.id.action_edit));
            menuItemEdit.setVisible(false);
            menuItemDelete=menu.findItem(R.id.action_delete);
            menuItemDelete.setVisible(false);




        }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Geri tuşuna basıldığında yapılacak işlemler
                requireActivity().getSupportFragmentManager().popBackStack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    private void editFood() {

        DbAdapter db= new DbAdapter(getActivity()) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };
        db.open();


        int id=R.layout.fragment_food_edit;
        setMainView(id);
        currentId=listCursor.getString(0);
        currentName=listCursor.getString(1);


        ((MainActivity2)getActivity()).getSupportActionBar().setTitle(currentName+"düzenle");

        String fields[] = new String[]{
                "_id",
                "besin_isim",
                "besin_porsiyon_büyüklügü_gram" ,
                        "besin_porsiyon_büyüklügü_ölcüsü_gram",
                        "besin_porsiyon_büyüklügü_adet",
                        "besin_porsiyon_büyüklügü_adet_ölcüsü",
                "besin_kalori",
                "besin_protein",
                "besin_yag",
                "besin_karbonhidrat",
                "besin_kalori_hesaplanmıs",
                "besin_protein_hesaplanmıs",
                "besin_karbonhidrat_hesaplanmıs",
                "besin_yag_hesaplanmıs"
        };

// String currentIdSQL = db.quoteSmart(currentId);
            Cursor foodCursor = db.select("food", fields, "_id", currentId);


            String StringId = foodCursor.getString(0);
            String Stringİsim = foodCursor.getString(1);
            String StringPorsiyonBuyuklugu = foodCursor.getString(2);
            String StringPorsiyonOlcusu = foodCursor.getString(3);
            String StringBesinPorsiyonİsimNumara = foodCursor.getString(4);
            String StringBesinPorsiyonİsimKelime = foodCursor.getString(5);
            String StringKalori = foodCursor.getString(6);
            String StringProtein = foodCursor.getString(7);
            String StringYag = foodCursor.getString(8);
            String StringCarb = foodCursor.getString(9);
            String StringKaloriHesaplanmıs = foodCursor.getString(10);
            String StringProteinHesaplanmıs = foodCursor.getString(11);
            String StringYagHesaplanmıs = foodCursor.getString(12);
            String StringCarbHesaplanmıs = foodCursor.getString(13);




        //db.close();

    }


    @SuppressLint("Range")
    private void populateListFood() {

        DbAdapter db= new DbAdapter(getActivity()) {
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
                "besin_isim",
                "besin_porsiyon_büyüklügü_gram" ,
                "besin_porsiyon_büyüklügü_ölcüsü_gram",
                "besin_porsiyon_büyüklügü_adet_ölcüsü",
                "besin_kalori_hesaplanmıs",
                "besin_protein_hesaplanmıs",
                "besin_yag_hesaplanmıs",
                "besin_karbonhidrat_hesaplanmıs"


        };

        listCursor=db.select("food",fields,"","","besin_isim","ASC");

        ListView lvItems=(ListView)getActivity().findViewById(R.id.listviewYemek) ;

        FoodCursorAdapter continentAdapter=new FoodCursorAdapter(getActivity(),listCursor);

        lvItems.setAdapter(continentAdapter);

            lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    listİtemClicked(arg2);
                }
            });



        db.close();
    }


    private void listİtemClicked(int listItemIdClicked) {

            int id=R.layout.fragment_food_view;
            setMainView(id);

        listCursor.moveToPosition(listItemIdClicked);

        currentId=listCursor.getString(0);
        currentName=listCursor.getString(1);
        String parentId=listCursor.getString(2);

        menuItemDelete.setVisible(true);
        menuItemEdit.setVisible(true);

        ((MainActivity2)getActivity()).getSupportActionBar().setTitle(currentName);

        DbAdapter db= new DbAdapter(getActivity()) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };
        db.open();

        String fields[] = new String[]{
                "_id",
                "besin_isim",
                "besin_porsiyon_büyüklügü_gram" ,
                "besin_porsiyon_büyüklügü_ölcüsü_gram",
                "besin_porsiyon_büyüklügü_adet",
                "besin_porsiyon_büyüklügü_adet_ölcüsü",
                "besin_kalori",
                "besin_protein",
                "besin_yag",
                "besin_karbonhidrat",
                "besin_kalori_hesaplanmıs",
                "besin_protein_hesaplanmıs",
                "besin_karbonhidrat_hesaplanmıs",
                "besin_yag_hesaplanmıs"
        };

// String currentIdSQL = db.quoteSmart(currentId);
        Cursor foodCursor = db.select("food", fields, "_id", currentId);

        if (foodCursor != null && foodCursor.moveToFirst()) {

            String StringId = foodCursor.getString(0);
            String Stringİsim = foodCursor.getString(1);
            String StringPorsiyonBuyuklugu = foodCursor.getString(2);
            String StringPorsiyonOlcusu = foodCursor.getString(3);
            String StringBesinPorsiyonİsimNumara = foodCursor.getString(4);
            String StringBesinPorsiyonİsimKelime = foodCursor.getString(5);
            String StringKalori = foodCursor.getString(6);
            String StringProtein = foodCursor.getString(7);
            String StringYag = foodCursor.getString(8);
            String StringCarb = foodCursor.getString(9);
            String StringKaloriHesaplanmıs = foodCursor.getString(10);
            String StringProteinHesaplanmıs = foodCursor.getString(11);
            String StringYagHesaplanmıs = foodCursor.getString(12);
            String StringCarbHesaplanmıs = foodCursor.getString(13);


            TextView textViewFoodAbout=(TextView) getView().findViewById(R.id.textViewFoodAbout);
            String foodAbout=StringPorsiyonBuyuklugu+" "+StringPorsiyonOlcusu+"="+StringBesinPorsiyonİsimNumara+""+StringBesinPorsiyonİsimKelime;
            textViewFoodAbout.setText(foodAbout);



            TextView textViewFoodName = (TextView) getView().findViewById(R.id.textViewFoodName);
            textViewFoodName.setText(Stringİsim);

            TextView textViewFoodEnergyYüzde=(TextView)getView().findViewById(R.id.textViewFoodEnergyYüzde);
            textViewFoodEnergyYüzde.setText(StringKalori);

            TextView textViewFoodproteinYüzde=(TextView)getView().findViewById(R.id.textViewFoodProteinYüzde);
            textViewFoodproteinYüzde.setText(StringProtein);

            TextView textViewFoodCarbYüzde=(TextView)getView().findViewById(R.id.textViewFoodCarbYüzde);
            textViewFoodCarbYüzde.setText(StringCarb);

            TextView textViewFoodYagYüzde=(TextView)getView().findViewById(R.id.textViewFoodFatYüzde);
            textViewFoodYagYüzde.setText(StringYag);



            TextView textViewFoodEnergyN=(TextView)getView().findViewById(R.id.textViewFoodEnergyN);
            textViewFoodEnergyN.setText(StringKaloriHesaplanmıs);

            TextView textViewFoodproteinN=(TextView)getView().findViewById(R.id.textViewProteinDiyetle);
            textViewFoodproteinN.setText(StringProteinHesaplanmıs);

            TextView textViewFoodCarbN=(TextView)getView().findViewById(R.id.textViewFoodCarbN);
            textViewFoodCarbN.setText(StringCarbHesaplanmıs);

            TextView textViewFoodYagN=(TextView)getView().findViewById(R.id.textViewFoodFatN);
            textViewFoodYagN.setText(StringYagHesaplanmıs);




        } else {
            Log.e("ERROR_TAG", "FoodCursor boş");
        }




        //db.close();



    }
}
