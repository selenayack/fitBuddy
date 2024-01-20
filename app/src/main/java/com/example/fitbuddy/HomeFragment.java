package com.example.fitbuddy;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    Cursor addFoodCursor;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

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

        FloatingActionButton buttonAddKahvaltı=view.findViewById(R.id.KahvatıAdd);

        buttonAddKahvaltı.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment yeniFragment = new AddFoodToDiaryFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, yeniFragment);
                transaction.addToBackStack(null); // Geri butonu ile geri dönülebilirlik ekler
                transaction.commit();


            }
        });
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






        return view;
    }




    private static final String PREFS_NAME = "FitBuddy15";
    private static final String DATABASE_LOADED_KEY = "database_load15";

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


}

