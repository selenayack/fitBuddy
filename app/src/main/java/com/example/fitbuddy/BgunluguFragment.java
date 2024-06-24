package com.example.fitbuddy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class BgunluguFragment extends Fragment {


    private View mainView;

    public BgunluguFragment() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainView = view;
        populateListKalori();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_bgunlugu, container, false);
        return mainView;
    }

    private void populateListKalori() {
        DbAdapter db = new DbAdapter(getActivity()) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };
        db.open();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        String firebaseUserId = firebaseUser.getUid();

        String[] fieldsYenenKalori = new String[]{
                "_id",
                "kalori_yenen_kalori",
                "kalori_yenen_tarih",
                "kalori_yenen_protein",
                "kalori_yenen_karbonhidrat",
                "kalori_yenen_yag"
        };

        String selection = "user_id = ?";
        String[] selectionArgs = new String[]{
                firebaseUserId
        };


        String orderBy = "kalori_yenen_tarih ASC";

        Cursor cursorFdYenenKalori = db.select("food_diary_kalori_yenen", fieldsYenenKalori, selection, selectionArgs, null, null, orderBy);

        if (cursorFdYenenKalori != null && cursorFdYenenKalori.moveToFirst()) {
            ListView lvItems = mainView.findViewById(R.id.listviewBgunlugu); // Use mainView to find ListView
            BGunluguCursorAdapter continentAdapter = new BGunluguCursorAdapter(getActivity(), cursorFdYenenKalori);
            lvItems.setAdapter(continentAdapter);
        } else {

        }

        db.close();

    }
    private void listİtemClicked(int arg2) {
    }


}