package com.example.fitbuddy;

import static com.example.fitbuddy.FoodDiaryAddFoodViewFragment.ARG_PARAM1;
import static com.example.fitbuddy.FoodDiaryAddFoodViewFragment.ARG_PARAM2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


public class AddFoodToDiaryFragment extends Fragment {

    Cursor addFoodCursor;
    Cursor listCursorFood;
    private String currentMealNumber;
    private View view;
    private String currentId;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_add_food_to_diary, container, false);
        ((MainActivity2) getActivity()).getSupportActionBar().setTitle("Besin Günlüğü");

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            currentMealNumber = bundle.getString("mealnumber");
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


        // Kategorinin altındaki besinleri listele


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

        // Besinlere tıklama olayı eklenebilir
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
            // Cursor boş değilse işlemleri gerçekleştir
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


            TextView textViewFoodAbout = (TextView) getView().findViewById(R.id.textViewFoodAbout);
            String foodAbout = StringPorsiyonBuyuklugu + " " + StringPorsiyonOlcusu + " = " + StringBesinPorsiyonİsimNumara + " " + StringBesinPorsiyonİsimKelime;
            textViewFoodAbout.setText(foodAbout);


            EditText editTextPorsiyonBuyuklugu = (EditText) getActivity().findViewById(R.id.editTextPorsiyonSayısı);
            editTextPorsiyonBuyuklugu.setText(StringBesinPorsiyonİsimNumara);

            EditText editTextPorsiyonÖlcüsü = (EditText) getActivity().findViewById(R.id.editTextGram);
            editTextPorsiyonÖlcüsü.setText(StringPorsiyonBuyuklugu);

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

            TextView textViewFoodproteinN = (TextView) getView().findViewById(R.id.textViewFoodProteinN);
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
                if (!(editable.toString().equals(""))) {
                    edittextPorsiyonSizeChanged();
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


    }

    /*private void edittextPorsiyonGramChanged() {


    }*/

    private void edittextPorsiyonSizeChanged() {

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

            Cursor foodCursor = db.select("food", fields, "_id=?", Arrays.toString(new String[]{String.valueOf(currentId)}));

            if (foodCursor != null && foodCursor.moveToFirst()) {

            // Cursor boş değilse işlemleri gerçekleştir
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


                EditText edittextGram=(EditText) getActivity().findViewById(R.id.editTextGram);
            String stringedittextGram=edittextGram.getText().toString();

        int error=0;
        double doublePorsiyonGram=0;
        try{
            doublePorsiyonGram=Double.parseDouble(stringedittextGram);

        }
        catch (NumberFormatException nfe){
            error=1;
            Toast.makeText(getActivity(),"gram sayı olmalı",Toast.LENGTH_SHORT).show();

        }

        if(stringedittextGram.equals("")){
            Toast.makeText(getActivity(),"gram boş olamaz",Toast.LENGTH_SHORT).show();

        }

            DateFormat df=new SimpleDateFormat("dd-MM-yyyy");
            String besin_tarih=df.format(Calendar.getInstance().getTime());
            String besinTarihSQL=db.quoteSmart(besin_tarih);

           /* Calendar cc=Calendar.getInstance();
            int yıl=cc.get(Calendar.YEAR);
            int ay=cc.get(Calendar.MONTH);
            int gün=cc.get(Calendar.DAY_OF_MONTH);*/

            String stringMealNumber=currentMealNumber;
            String stringMealNumberSQL=db.quoteSmart(stringMealNumber);

            String stringFoodId=currentId;
            String stringFoodIdSQL=db.quoteSmart(stringFoodId);


            String fd_porsiyon_büyüklügü_gram=edittextGram.getText().toString();

            String fd_porsiyon_büyüklügü_gramSQL= db.quoteSmart(fd_porsiyon_büyüklügü_gram);
            String fd_porsiyon_büyüklügü_ölcüsü_gramSQL=db.quoteSmart(StringPorsiyonOlcusu);


            double doublePorsiyonBuyuluguGram=0;
            try{
                doublePorsiyonBuyuluguGram=Double.parseDouble(stringedittextGram);
            }

            catch(NumberFormatException nfe){
                System.out.println("parse edilemedi"+nfe);
            }

            double doublePorsiyonSayısıAdet=Math.round(doublePorsiyonGram/doublePorsiyonBuyuluguGram);
            String stringFdServingSizePcs=""+doublePorsiyonSayısıAdet;
            String stringFdServingSizePcsSql=db.quoteSmart(stringFdServingSizePcs);


            String stringFdServingSizePcsOlcuSql=db.quoteSmart(StringBesinPorsiyonİsimKelime);

            double doubleEnergyPerHundred=Double.parseDouble( StringKalori);
            double doubleEnergyCalculated=(doublePorsiyonBuyuluguGram*doubleEnergyPerHundred);
            Toast.makeText(getActivity(),"Energy"+doubleEnergyCalculated,Toast.LENGTH_SHORT).show();


        }}

        @Override
        public void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }
        }


    }

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
                    "besin_porsiyon_büyüklügü",
                    "besin_porsiyon_ölcüsü",
                    "besin_porsiyon_isim_kelime",
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
