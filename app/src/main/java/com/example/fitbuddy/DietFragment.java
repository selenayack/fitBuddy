package com.example.fitbuddy;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DietFragment extends Fragment {

    private DbAdapter  dbAdapter;
    private ModelInterpreter modelInterpreter;
    private TextView resultTextView;
    private static List<List<String>> dietLists;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "DietPreferences";
    private static final String DIET_LIST_KEY = "DietList";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Diyet listelerini sadece bir kez oluştur
        if (dietLists == null) {
            initializeDietLists();
        }

        sharedPreferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    private void initializeDietLists() {
        dietLists = new ArrayList<>();

        // Diyet Listesi 1
        List<String> dietList1 = new ArrayList<>();
        dietList1.add("Diyet Listesi 1 - Menü 1: Kahvaltı - Yulaf ezmesi, Öğle - Tavuk salatası, Akşam - Izgara balık");
        dietList1.add("Diyet Listesi 1 - Menü 2: Kahvaltı - Smoothie, Öğle - Sebze çorbası, Akşam - Hindi göğsü");
        dietList1.add("Diyet Listesi 1 - Menü 3: Kahvaltı - Yumurta ve avokado, Öğle - Kinoalı salata, Akşam - Tavuk göğsü");

        // Diyet Listesi 2
        List<String> dietList2 = new ArrayList<>();
        dietList2.add("Diyet Listesi 2 - Menü 1: Kahvaltı - Meyveli yoğurt, Öğle - Mercimek çorbası, Akşam - Izgara tavuk");
        dietList2.add("Diyet Listesi 2 - Menü 2: Kahvaltı - Tam tahıllı ekmek ve peynir, Öğle - Tavuklu wrap, Akşam - Somon");
        dietList2.add("Diyet Listesi 2 - Menü 3: Kahvaltı - Fıstık ezmeli tost, Öğle - Nohutlu salata, Akşam - Sebzeli makarna");

        // Diyet Listesi 3
        List<String> dietList3 = new ArrayList<>();
        dietList3.add("Diyet Listesi 3 - Menü 1: Kahvaltı - Chia tohumlu puding, Öğle - Tavuklu sandviç, Akşam - Izgara köfte");
        dietList3.add("Diyet Listesi 3 - Menü 2: Kahvaltı - Omlet, Öğle - Kinoa salatası, Akşam - Izgara balık");
        dietList3.add("Diyet Listesi 3 - Menü 3: Kahvaltı - Muzlu smoothie, Öğle - Falafel, Akşam - Izgara tavuk");

        // Diyet Listesi 4
        List<String> dietList4 = new ArrayList<>();
        dietList4.add("Diyet Listesi 4 - Menü 1: Kahvaltı - Yulaf ezmesi ve çilek, Öğle - Ton balıklı salata, Akşam - Sebzeli tavuk");
        dietList4.add("Diyet Listesi 4 - Menü 2: Kahvaltı - Yoğurt ve ceviz, Öğle - Tavuklu wrap, Akşam - Sebzeli köfte");
        dietList4.add("Diyet Listesi 4 - Menü 3: Kahvaltı - Meyve tabağı, Öğle - Tavuklu sandviç, Akşam - Izgara somon");

        // Diyet listelerini ana listeye ekle
        dietLists.add(dietList1);
        dietLists.add(dietList2);
        dietLists.add(dietList3);
        dietLists.add(dietList4);
    }




    private void displayDietList(int prediction) {
        if (prediction >= 1 && prediction <= 4) {
            String storedDiet = sharedPreferences.getString(DIET_LIST_KEY + prediction, null);
            if (storedDiet == null) {
                List<String> selectedDietList = dietLists.get(prediction - 1);
                Random random = new Random();
                String randomMenu = selectedDietList.get(random.nextInt(selectedDietList.size()));
                sharedPreferences.edit().putString(DIET_LIST_KEY + prediction, randomMenu).apply();
                resultTextView.setText(randomMenu);
            } else {
                resultTextView.setText(storedDiet);
            }
        } else {
            resultTextView.setText("Geçersiz çıktı");
        }
    }





        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_diet, container, false);

            resultTextView = view.findViewById(R.id.result_text_view);

            dbAdapter = new DbAdapter(getContext()) {
                @Override
                public void onCreate(SQLiteDatabase sqLiteDatabase) {

                }

                @Override
                public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

                }
            };
            dbAdapter.open();

            try {
                modelInterpreter = new ModelInterpreter(getContext());
            } catch (IOException e) {
                e.printStackTrace();
                resultTextView.setText("Model yüklenirken bir hata oluştu.");
                return view;
            }

            FirebaseAuth mAuth = FirebaseAuth.getInstance();

            FirebaseUser firebaseUser = mAuth.getCurrentUser();

            String firebaseUserId = firebaseUser.getUid();



           int userId = (int) dbAdapter.getUserIdFromFirebaseId(firebaseUserId);


            float[] userData = dbAdapter.getUserData(userId);
            if (userData != null && userData.length == 5) {
                int prediction = modelInterpreter.predict(userData);
                displayDietList(prediction);
            } else {
                resultTextView.setText("Kullanıcı verileri alınamadı.");
            }




            LinearLayout linearLayout1 = view.findViewById(R.id.linearlayout1);

            linearLayout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), Diet1.class);
                    startActivity(intent);
                }
            });


    LinearLayout linearLayout2 = view.findViewById(R.id.linearlayout2);


            linearLayout2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getActivity(), Diet2.class);
            startActivity(intent);
        }
    });
            LinearLayout linearLayout3 = view.findViewById(R.id.linearlayout3); // Bu kısmı kendi layoutunuzdaki LinearLayout'a uygun ID ile değiştirin

            linearLayout3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), Diet3.class);
                    startActivity(intent);
                }
            });
            LinearLayout linearLayout4 = view.findViewById(R.id.linearlayout4);


            linearLayout4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), Diet4.class);
                    startActivity(intent);
                }
            });

            LinearLayout linearLayout5 = view.findViewById(R.id.linearlayout5);

            linearLayout5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), Diet5.class);
                    startActivity(intent);
                }
            });


            return view;
}
}


