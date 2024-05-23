package com.example.fitbuddy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


public class AddFoodToDiaryFragment extends Fragment {

    Cursor addFoodCursor;
    Cursor listCursorFood;
    private Integer currentMealNumber;
    private View view;
    private String currentId;



    private boolean lockPortionSizePcs;
    private boolean lockPortionSizeGram;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String currentPorsiyonSizePcs;
    private String currentPorsiyonSizeGram;




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_add_food_to_diary, container, false);

        DbAdapter db = new DbAdapter(getActivity()) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };
        db.open();
        //db.delete("food_diary", null, null);

        ((MainActivity2) getActivity()).getSupportActionBar().setTitle("Besin Günlüğü");
        Bundle bundle = getArguments();
        if (bundle != null) {
            currentMealNumber = bundle.getInt("mealNumber"); // getInt kullanarak mealNumber'ı alıyoruz
            //Toast.makeText(getActivity(), "Mealnumberr: " + currentMealNumber, Toast.LENGTH_SHORT).show();
        }



        addFood("0", " ");


        return view;
    }


    private void setMainView(int id) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(id, null);
        ViewGroup rootView = (ViewGroup) getView();
        rootView.removeAllViews();
        rootView.addView(view);

    }


    private void addFood(String stringCategoryparentId, String stringCategoryName) {

        //Toast.makeText(getActivity(),"kahvaltıı",Toast.LENGTH_SHORT).show();
        DbAdapter db = new DbAdapter(getActivity()) {
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
                "category_name",
                "category_parent_id"
        };

        addFoodCursor = db.select("categories", fields, "category_parent_id", stringCategoryparentId, "category_name", "ASC");

        ArrayList<String> values = new ArrayList<String>();

        int categoriesCount = addFoodCursor.getCount();

        int categoryNameIndex = addFoodCursor.getColumnIndex("category_name");
        if (addFoodCursor.moveToFirst()) {
            for (int i = 0; i < categoriesCount; i++) {

                values.add(addFoodCursor.getString(categoryNameIndex));

               /* Toast.makeText(getActivity(), "Id:" + categoriesCursor.getString(0) + "\n" +
                                "İsim:" + categoriesCursor.getString(1),
                        Toast.LENGTH_SHORT).show();*/

                addFoodCursor.moveToNext();
            }

        }

        //categoriesCursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, values);

        ListView lv = view.findViewById(R.id.listviewAddFoodToDiary);
        lv.setAdapter(adapter);


        if (stringCategoryparentId.equals("0")) {
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    listİtemClicked(arg2);
                }
            });
        }

        db.close();


    }

    private void listİtemClicked(int listItemIdClicked) {
        //Toast.makeText(getActivity(),"ID"+listItemIdClicked, Toast.LENGTH_SHORT).show();


        DbAdapter db = new DbAdapter(getActivity()) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };
        db.open();




        addFoodCursor.moveToPosition(listItemIdClicked);

        @SuppressLint("Range") String selectedCategoryId = addFoodCursor.getString(addFoodCursor.getColumnIndex("_id"));
        @SuppressLint("Range") String selectedCategoryName = addFoodCursor.getString(addFoodCursor.getColumnIndex("category_name"));

        listFoodsInCategory(selectedCategoryId);

        String currentId = addFoodCursor.getString(0);
        String name = addFoodCursor.getString(1);
        String parentId = addFoodCursor.getString(2);
        ((MainActivity2) getActivity()).getSupportActionBar().setTitle(name + " " + "ekle");
        addFood(currentId, name);


        //showFoodInCategory(currentId,name,parentId);


    }

    private void listFoodsInCategory(String categoryId) {
        DbAdapter db = new DbAdapter(getActivity()) {
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
                "besin_porsiyon_büyüklügü_adet_ölcüsü",
                "besin_kalori_hesaplanmıs",
                "besin_protein_hesaplanmıs",
                "besin_yag_hesaplanmıs",
                "besin_karbonhidrat_hesaplanmıs"


        };

        listCursorFood = db.select("food", fields, "besin_kategori_id", categoryId, "besin_isim", "ASC");

        ArrayList<String> foodValues = new ArrayList<String>();

        int foodCount = listCursorFood.getCount();

        int foodNameIndex = listCursorFood.getColumnIndex("besin_isim");
        if (listCursorFood.moveToFirst()) {
            for (int i = 0; i < foodCount; i++) {
                foodValues.add(listCursorFood.getString(foodNameIndex));
                listCursorFood.moveToNext();
            }
        }

        ArrayAdapter<String> foodAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, foodValues);

        ListView foodListView = view.findViewById(R.id.listviewFoodsInCategory);
        foodListView.setAdapter(foodAdapter);

        FoodCursorAdapter continentAdapter = new FoodCursorAdapter(getActivity(), listCursorFood);

        foodListView.setAdapter(continentAdapter);


        foodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                foodListItemClicked(arg2);
            }
        });

        db.close();
    }

    private void foodListItemClicked(int listItemIdClicked) {
        int id = R.layout.fragment_food_diary_add_food_view;
        setMainView(id);

        listCursorFood.moveToPosition(listItemIdClicked);

        String currentId = listCursorFood.getString(0);
        String currentName = listCursorFood.getString(1);
        String parentId = listCursorFood.getString(2);


        ((MainActivity2) getActivity()).getSupportActionBar().setTitle(currentName + " ekle");

        DbAdapter db = new DbAdapter(getActivity()) {
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
            String StringYagHesaplanmıs = foodCursor.getString(13);
            String StringCarbHesaplanmıs = foodCursor.getString(12);


            TextView textViewFoodAbout = (TextView) getView().findViewById(R.id.textViewFoodAbout);
            String foodAbout = StringPorsiyonBuyuklugu + " " + StringPorsiyonOlcusu + " = " + StringBesinPorsiyonİsimNumara + " " + StringBesinPorsiyonİsimKelime;
            textViewFoodAbout.setText(foodAbout);


            EditText editTextPorsiyonBuyuklugu = (EditText) getActivity().findViewById(R.id.editTextPorsiyonSayısı);
            editTextPorsiyonBuyuklugu.setText(StringBesinPorsiyonİsimNumara);
            currentPorsiyonSizePcs=StringBesinPorsiyonİsimNumara;

            EditText editTextPorsiyonÖlcüsü = (EditText) getActivity().findViewById(R.id.editTextGram);
            editTextPorsiyonÖlcüsü.setText(StringPorsiyonBuyuklugu);
            currentPorsiyonSizeGram=StringPorsiyonBuyuklugu;

            TextView textViewPorsiyonOlcusu = (TextView) getActivity().findViewById(R.id.textViewOlcuBirimi2);
            textViewPorsiyonOlcusu.setText(StringBesinPorsiyonİsimKelime);


            TextView textViewFoodName = (TextView) getView().findViewById(R.id.textViewFoodName);
            textViewFoodName.setText(Stringİsim);

            TextView textViewFoodEnergyYüzde = (TextView) getView().findViewById(R.id.textViewFoodEnergyYüzde);
            textViewFoodEnergyYüzde.setText(StringKalori);

            TextView textViewFoodproteinYüzde = (TextView) getView().findViewById(R.id.textViewFoodProteinYüzde);
            textViewFoodproteinYüzde.setText(StringProtein);

            TextView textViewFoodCarbYüzde = (TextView) getView().findViewById(R.id.textViewFoodCarbYüzde);
            textViewFoodCarbYüzde.setText(StringCarb);

            TextView textViewFoodYagYüzde = (TextView) getView().findViewById(R.id.textViewFoodFatYüzde);
            textViewFoodYagYüzde.setText(StringYag);


            TextView textViewFoodEnergyN = (TextView) getView().findViewById(R.id.textViewFoodEnergyN);
            textViewFoodEnergyN.setText(StringKaloriHesaplanmıs);

            TextView textViewFoodproteinN = (TextView) getView().findViewById(R.id.textViewProteinDiyetle);
            textViewFoodproteinN.setText(StringProteinHesaplanmıs);

            TextView textViewFoodCarbN = (TextView) getView().findViewById(R.id.textViewFoodCarbN);
            textViewFoodCarbN.setText(StringCarbHesaplanmıs);

            TextView textViewFoodYagN = (TextView) getView().findViewById(R.id.textViewFoodFatN);
            textViewFoodYagN.setText(StringYagHesaplanmıs);


            // Diğer işlemleri burada devam ettirin
        } else {
            Log.e("ERROR_TAG", "FoodCursor boş");
        }


        EditText editTextPorsiyonSayısı = (EditText) getActivity().findViewById(R.id.editTextPorsiyonSayısı);
        editTextPorsiyonSayısı.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                    edittextPorsiyonSizeChanged();



            }
        });
        
        editTextPorsiyonSayısı.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    
                }
                else{
                    String lock="portionSizePcs";
                    releaseLock(lock);
                }
            }
        });
        EditText editTextPorsiyonGram = (EditText) getActivity().findViewById(R.id.editTextGram);
        editTextPorsiyonGram.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {


                    edittextPorsiyonGramChanged();



            }
        });
        editTextPorsiyonGram.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){

                }
                else{
                    String lock="portionSizeGram";
                    releaseLock(lock);
                }
            }
        });



        Button buttonEkle = (Button) getActivity().findViewById(R.id.buttonEkle);
        buttonEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFoodToDiary();
            }
        });


        //db.close();

        //Toast.makeText(getActivity(),"pcs"+currentPorsiyonSizePcs+"gram"+currentPorsiyonSizeGram,Toast.LENGTH_LONG).show();


    }

    private void releaseLock(String lock) {
        if(lock.equals("portionSizeGram")){
            lockPortionSizeGram=false;
        }
        else{
            lockPortionSizePcs=false;
        }
    }

    /*private void edittextPorsiyonGramChanged() {


    }*/

    private void edittextPorsiyonSizeChanged() {

        if(!(lockPortionSizeGram)) {

        lockPortionSizePcs=true;


        String currentId = listCursorFood.getString(0);

        EditText editTextPorsiyonBuyuklugu =view.findViewById(R.id.editTextPorsiyonSayısı);
        String stringPorsiyonSayisi = editTextPorsiyonBuyuklugu.getText().toString();

        double doubleporsiyonSayisi = 0;
        try {
            doubleporsiyonSayisi = Double.parseDouble(stringPorsiyonSayisi);
        } catch (NumberFormatException nfe) {
            System.out.println("parse edilemedi" + nfe);
            return;  // Eğer parse işlemi başarısız olursa, geri kalan işlemleri yapmamak için metottan çık
        }

        EditText editTextPorsiyonÖlcüsü = view.findViewById(R.id.editTextGram);

        DbAdapter db = new DbAdapter(getActivity()) {
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
                "besin_porsiyon_büyüklügü_gram" ,
                "besin_porsiyon_büyüklügü_ölcüsü_gram",
                "besin_porsiyon_büyüklügü_adet",
                "besin_porsiyon_büyüklügü_adet_ölcüsü",
        };

        String currentIdSQL = db.quoteSmart(currentId);
        double doubleServingNameNumber = 0;
        Cursor foodCursor = db.select("food", fields, "_id", currentId);

        if (foodCursor != null && foodCursor.moveToFirst()) {
            do {
                String StringPorsiyonBuyuklugu = foodCursor.getString(1);


                try {
                    doubleServingNameNumber = Double.parseDouble(StringPorsiyonBuyuklugu);
                } catch (NumberFormatException nfe) {
                    System.out.println("parse edilemedi" + nfe);
                    continue;  // Eğer parse işlemi başarısız olursa, diğer kayıta geç ve döngüyü devam ettir
                }

                double doubleporsiyonGram = doubleporsiyonSayisi * doubleServingNameNumber;
                editTextPorsiyonÖlcüsü.setText(String.valueOf(doubleporsiyonGram));

            } while (foodCursor.moveToNext());

        } else {
            System.out.println("cursor boşş");
        }

        db.close();


        }}
    private void edittextPorsiyonGramChanged() {


        if(!(lockPortionSizePcs)) {

            lockPortionSizeGram=true;
            String currentId = listCursorFood.getString(0);


            EditText editTextPorsiyonGram = view.findViewById(R.id.editTextGram);
            String stringPorsiyonGram = editTextPorsiyonGram.getText().toString();

            double doubleporsiyonGram = 0;
            try {
                doubleporsiyonGram = Double.parseDouble(stringPorsiyonGram);
            } catch (NumberFormatException nfe) {
                System.out.println("parse edilemedi" + nfe);
                return;  // Eğer parse işlemi başarısız olursa, geri kalan işlemleri yapmamak için metottan çık
            }

            EditText editTextPorsiyonSayisi = (EditText) getActivity().findViewById(R.id.editTextPorsiyonSayısı);


            DbAdapter db = new DbAdapter(getActivity()) {
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
                    "besin_porsiyon_büyüklügü_gram",
                    "besin_porsiyon_büyüklügü_ölcüsü_gram",
                    "besin_porsiyon_büyüklügü_adet",
                    "besin_porsiyon_büyüklügü_adet_ölcüsü",
            };

            String currentIdSQL = db.quoteSmart(currentId);
            Cursor foodCursor = db.select("food", fields, "_id", currentId);

            //Toast.makeText(getActivity(),"pcs"+currentPorsiyonSizePcs+"gram"+doubleporsiyonGram,Toast.LENGTH_LONG).show();

            double doubleServingNameNumber = 0;

            if (foodCursor != null && foodCursor.moveToFirst()) {
                do {
                    String StringPorsiyonBuyuklugu = foodCursor.getString(1);


                    try {
                        doubleServingNameNumber = Double.parseDouble(StringPorsiyonBuyuklugu);
                    } catch (NumberFormatException nfe) {
                        System.out.println("parse edilemedi" + nfe);
                        continue;  // Eğer parse işlemi başarısız olursa, diğer kayıta geç ve döngüyü devam ettir
                    }


                } while (foodCursor.moveToNext());

            } else {
                System.out.println("cursor boşş");
            }


            int intPorsiyon = (int) (doubleporsiyonGram / doubleServingNameNumber);
            editTextPorsiyonSayisi.setText(String.valueOf(intPorsiyon));


            //Toast.makeText(getActivity(), "pcssss" + doubleServingNameNumber + "gram" + doubleporsiyonGram + "PORSİYON" + doublePorsiyon, Toast.LENGTH_LONG).show();

            //db.close();

        }
    }



    private void addFoodToDiary () {

        DbAdapter db = new DbAdapter(getActivity()) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {
            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            }
        };
        db.open();

        String currentId = listCursorFood.getString(0);

        String fields[] = new String[]{
                "_id",
                "besin_isim",
                "besin_porsiyon_büyüklügü_gram",
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

        //String currentIdSQL = db.quoteSmart(currentId);
        Cursor foodCursor = db.select("food", fields, "_id", currentId);

        if (foodCursor != null && foodCursor.moveToFirst()) {


            String StringId = foodCursor.getString(0);
            String Stringİsim = foodCursor.getString(1);
            String StringPorsiyonBuyukluguGram = foodCursor.getString(2);
            String StringPorsiyonOlcusuGram = foodCursor.getString(3);
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


            int error = 0;

            EditText editTextPorsiyongram = (EditText) getActivity().findViewById(R.id.editTextGram);
            String fdPorsiyonGram = editTextPorsiyongram.getText().toString();
            String fdPorsiyonGramSQL = db.quoteSmart(fdPorsiyonGram);

            double doublePorsiyonGram = 0;

            try {
                doublePorsiyonGram = Double.parseDouble(fdPorsiyonGram);
            } catch (NumberFormatException nfe) {
                error = 1;
                Toast.makeText(getActivity(), "Lütfen gram için sayı giriniz", Toast.LENGTH_SHORT).show();
            }
            if (fdPorsiyonGram.equals("")) {
                error = 1;
                Toast.makeText(getActivity(), "Gram boş olamaz", Toast.LENGTH_SHORT).show();

            }


            //tarih


           /* Calendar calendar = Calendar.getInstance();
            int yıl = calendar.get(Calendar.YEAR);
            int ay = calendar.get(Calendar.MONTH);
            int gün = calendar.get(Calendar.DAY_OF_MONTH);*/

            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String stringFdDate  = df.format(Calendar.getInstance().getTime());


            String stringFdDateSql = db.quoteSmart(stringFdDate);

            //öğün numarası
            String string_fd_meal_number = currentMealNumber.toString();
            String fdmealNumberSQL = db.quoteSmart(string_fd_meal_number);

            //food id
            String stringFdFoodId = currentId;
            String stringFdFoodIdSQL = db.quoteSmart(stringFdFoodId);

            //Porsiyon gram
            String fdServingSizeGramÖlçüsüSQL = db.quoteSmart(StringPorsiyonOlcusuGram);

            //Porsiyon adet
            double doubleporsiyonAsılGram = 0;
            try {
                doubleporsiyonAsılGram = Double.parseDouble(StringPorsiyonBuyukluguGram);
            } catch (NumberFormatException nfe) {
                System.out.println("parse edilemedi" + nfe);
                return;  // Eğer parse işlemi başarısız olursa, geri kalan işlemleri yapmamak için metottan çık
            }




            double doublePorsiyonAdet = Math.round(doublePorsiyonGram / doubleporsiyonAsılGram);
            String stringFdPorsiyonAdet = "" + doublePorsiyonAdet;
            String stringFdPorsiyonAdetSQL = db.quoteSmart(stringFdPorsiyonAdet);

            //porsiyon olcusu
            String fdServingSizePorsiyonÖlçüsüSQL = db.quoteSmart(StringBesinPorsiyonİsimKelime);


            //energy
            double doubleEnergyYüzde = Double.parseDouble(StringKalori);
            double doubleFdKaloriHesaplanmış = (doublePorsiyonGram * doubleEnergyYüzde) / 100;
            int intFdKloriHesaplanmış = (int) doubleFdKaloriHesaplanmış;

            String stringFdKaloriHesaplanmış = "" + intFdKloriHesaplanmış;
            String stringFdKaloriHesaplanmışSQL = db.quoteSmart(stringFdKaloriHesaplanmış);


            //protein
            double doubleProteinYüzde = Double.parseDouble(StringProtein);
            double doubleFdProteinHesaplanmış = (doublePorsiyonGram * doubleProteinYüzde) / 100;
            int intFdProteinHesaplanmış = (int) doubleFdProteinHesaplanmış;

            String stringFdProteinHesaplanmış = "" + intFdProteinHesaplanmış;
            String stringFdProteinHesaplanmışSQL = db.quoteSmart(stringFdProteinHesaplanmış);

            //karbonhidrat
            double doubleCarbYüzde = Double.parseDouble(StringCarb);
            double doubleFdCarbHesaplanmış = (doublePorsiyonGram * doubleCarbYüzde) / 100;
            int intFdCarbHesaplanmış = (int) doubleFdCarbHesaplanmış;
            String stringFdCarbHesaplanmış = "" + intFdCarbHesaplanmış;
            String stringFdCarbHesaplanmışSQL = db.quoteSmart(stringFdCarbHesaplanmış);


            //yag
            double doubleYagYüzde = Double.parseDouble(StringYag);
            double doubleFdYagHesaplanmış = (doublePorsiyonGram * doubleYagYüzde) / 100;
            int intFdYagHesaplanmış = (int) doubleFdYagHesaplanmış;
            String stringFdYagHesaplanmış = "" +intFdYagHesaplanmış;
            String stringFdYagHesaplanmışSQL = db.quoteSmart(stringFdYagHesaplanmış);


            if (error == 0) {
                String impFields = "_id,fd_tarih,fd_ögün_numara,fd_besin_id ," +
                        "fd_porsiyon_büyüklügü_gram,fd_porsiyon_büyüklügü_ölcüsü_gram," +
                        "fd_porsiyon_büyüklügü_adet,fd_porsiyon_büyüklügü_ölcüsü_adet," +
                        "fd_kalori_hesaplanmıs,fd_protein_hesaplanmıs," +
                        "fd_karbonhidrat_hesaplanmıs,fd_yag_hesaplanmıs";
                String impValues = "NULL," + stringFdDateSql + ","+fdmealNumberSQL+","+stringFdFoodIdSQL + "," +
                        fdPorsiyonGramSQL + "," + fdServingSizeGramÖlçüsüSQL + "," +
                        stringFdPorsiyonAdetSQL + "," + fdServingSizePorsiyonÖlçüsüSQL + "," +
                        stringFdKaloriHesaplanmışSQL + "," + stringFdProteinHesaplanmışSQL + "," +
                        stringFdCarbHesaplanmışSQL + "," + stringFdYagHesaplanmışSQL;


                db.insert("food_diary", impFields, impValues);
                Toast.makeText(getActivity(), "Besin Günlüğü güncellendi", Toast.LENGTH_SHORT).show();


                //Toast.makeText(getActivity(),"kalori"+doubleFdKaloriHesaplanmış,Toast.LENGTH_SHORT).show();

            } else {
                Log.e("ERROR_TAG", "FoodCursor boş");
            }

            Fragment yeniFragment = new HomeFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.container, yeniFragment);
            transaction.addToBackStack(null); // Geri butonu ile geri dönülebilirlik ekler
            transaction.commit();


            db.close();


        }


    }}

   /* private void showFoodInCategory(String currentId, String name, String parentId) {

        if(parentId.equals("0")){

           int id=R.layout.fragment_yemek;
            setMainView(id);

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
                    "besin_porsiyon_büyüklügü_gram",
                    "besin_porsiyon_büyüklügü_ölcüsü_gram",
                    "besin_porsiyon_büyüklügü_adet",
                    "besin_porsiyon_büyüklügü_adet_ölcüsü",
                    "besin_kalori_hesaplanmıs",
                    "besin_protein_hesaplanmıs",
                    "besin_yag_hesaplanmıs",
                    "besin_karbonhidrat_hesaplanmıs"


            };

            listCursorFood=db.select("food",fields,"","","besin_isim","ASC");

            ListView lvItems=(ListView)getActivity().findViewById(R.id.listviewYemek) ;

            FoodCursorAdapter continentAdapter=new FoodCursorAdapter(getActivity(),listCursorFood);

            lvItems.setAdapter(continentAdapter);

            lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    listİtemClicked(arg2);
                }
            });
        }

    }*/
