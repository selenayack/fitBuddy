package com.example.fitbuddy;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Calendar;

public class HedefFragment extends Fragment {
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_hedef, container, false);

        ((MainActivity2)getActivity()).getSupportActionBar().setTitle("Hedefler");


        Button buttonHesapla = view.findViewById(R.id.buttonEkleee);
        buttonHesapla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bmrhesapla();

            }
        });


        //errorMesajınıSakla();

        kullanılanOlcu();

        return view;



    }

    private void kullanılanOlcu() {

        DbAdapter db = new DbAdapter(getActivity()) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };
        db.open();
        long sutunId = 1;

        String fields[] = new String[]{
                "_id",
                "user_olcu"

        };

        Cursor c = db.selectPrimaryKey("USER", "_id", sutunId, fields);
        String olcu;
        olcu = c.getString(1);
        //Toast.makeText(this,"Olcu: "+olcu,Toast.LENGTH_LONG).show();

        if (olcu.startsWith("k")) {

        } else {

            TextView textViewOlcuBirimi = (TextView) getActivity().findViewById(R.id.textViewOlcuBirimi2);
            textViewOlcuBirimi.setText("pounds");
            TextView textViewHaftalikKg = (TextView)  getActivity().findViewById(R.id.textViewHaftalikKg2);
            textViewHaftalikKg.setText("pounds");

        }


    }



    private void Bmrhesapla() {


            TextView textViewErrorMessage = (TextView) getActivity().findViewById(R.id.textViewErrorMessage2);
            textViewErrorMessage.setVisibility(View.GONE);

            String errorMessage = " ";

            EditText editTextMevcutKilo = (EditText) getActivity().findViewById(R.id.editTextMevcutKilo);
            String stringMevcutKilo = editTextMevcutKilo.getText().toString();
            double doubleMevcutKilo = 0;
            try {
                doubleMevcutKilo = Double.parseDouble(stringMevcutKilo);
            } catch (NumberFormatException nfe) {
                errorMessage = "mevcut kilo sayı olmalı";

            }

            Spinner spinnerHedef = (Spinner) getActivity().findViewById(R.id.spinnerhedef);
            int intHedef = spinnerHedef.getSelectedItemPosition();

            Spinner spinnerHaftalikKg = (Spinner) getActivity().findViewById(R.id.spinnerHaftalıkKg2);
            String stringHaftalikKg = spinnerHaftalikKg.getSelectedItem().toString();


            //if (errorMessage.isEmpty()) {


            DbAdapter db = new DbAdapter(getActivity()) {
                @Override
                public void onCreate(SQLiteDatabase sqLiteDatabase) {

                }

                @Override
                public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

                }
            };
            db.open();


            long hedefId = 1;

            double doubleMevcutKiloSQL = db.quoteSmart(doubleMevcutKilo);
            boolean updateMevcutKilo = db.update("hedef", "_id", hedefId, "hedef_mevcut_kilo", doubleMevcutKiloSQL);
            Log.d("calculate_bmr", "hedef_mevcut_kilo güncellendi: " + updateMevcutKilo);

            int intHedefSQL = db.quoteSmart(intHedef);
            boolean updateHedef = db.update("hedef", "_id", hedefId, "hedef_yapılmak_istenen", intHedefSQL);
            Log.d("calculate_bmr", "hedef_yapılmak_istenen güncellendi: " + updateHedef);

            String stringHaftalikKgSQL = db.quoteSmart(stringHaftalikKg);
            boolean updateHaftalikKg = db.update("hedef", "_id", hedefId, "hedef_haftalık_kilo", stringHaftalikKgSQL);
            Log.d("calculate_bmr", "hedef_haftalık_kg güncellendi: " + updateHaftalikKg);


            long sutunId = 1;

            String fields[] = new String[]{
                    "_id",
                    "user_dogum_tarih",
                    "user_cinsiyet",
                    "user_boy",
                    "user_aktivite_derecesi "


            };
            Cursor c = db.selectPrimaryKey("USER", "_id", sutunId, fields);
            String stringUserDogum = c.getString(1);
            String stringUserCinsiyet = c.getString(2);
            String stringUserBoy = c.getString(3);
            String stringUserAktivite = c.getString(4);

            System.out.println("dogum" + stringUserDogum);


            //Get yas
            String[] items1 = stringUserDogum.split("-");
            String stringDay = items1[0];
            String stringMonth = items1[1];
            String stringYear = items1[2];
            //yılı da doğru aldı


            int intYear = 0;
            try {
                intYear = Integer.parseInt(stringYear);

            } catch (NumberFormatException nfe) {
                System.out.println("Parse edilemedi");
            }

            //System.out.println("yıl"+intYear);  bu da doğru


            int intMonth = 0;
            try {
                intMonth = Integer.parseInt(stringMonth);

            } catch (NumberFormatException nfe) {
                System.out.println("Parse edilemedi");
            }
            int intDay = 0;
            try {
                intDay = Integer.parseInt(stringDay);

            } catch (NumberFormatException nfe) {
                System.out.println("Parse edilemedi");
            }


            int intYass = getYas(intDay, intMonth, intYear);
            String stringUserYas = String.valueOf(intYass);


            //boy

            double doubleBoy = 0;
            try {
                doubleBoy = Integer.parseInt(stringUserBoy);

            } catch (NumberFormatException nfe) {
                System.out.println("Parse edilemedi");
            }

            //yas
            int intYas = 0;
            try {
                intYas = Integer.parseInt(stringUserYas);

            } catch (NumberFormatException nfe) {
                System.out.println("Parse edilemedi");
            }


            //Toast.makeText(this,"dogum tarihi="+stringUserDogum+"\nYas"+stringUserYas+"\nCinsiyet="+stringUserCinsiyet+"\nBoy="+stringUserBoy+"\nAktivite="+stringUserAktivite,Toast.LENGTH_LONG).show();


            //bmr hesaplama aktivitesiz

            double BMR = 0;
            if (stringUserCinsiyet.equals("Erkek")) {
                BMR = (10 * doubleMevcutKilo) + (6.25 * doubleBoy) - (5 * intYas) + 5;

            } else {
                BMR = (10 * doubleMevcutKilo) + (6.25 * doubleBoy) - (5 * intYas) - 161;
            }

            BMR = Math.round(BMR);
            double kaloriAktivitesizSQL = db.quoteSmart(BMR);


            db.update("hedef", "_id", hedefId, "hedef_kalori", kaloriAktivitesizSQL);


            double protein = Math.round(BMR * 25 / 100);
            double karbonhidrat = Math.round(BMR * 50 / 100);
            double yag = Math.round(BMR * 25 / 100);

            double proteinSQL = db.quoteSmart(protein);
            double karbSQL = db.quoteSmart(karbonhidrat);
            double yagSQL = db.quoteSmart(yag);

            db.update("hedef", "_id", hedefId, "hedef_protein", proteinSQL);
            db.update("hedef", "_id", hedefId, "hedef_carb", karbSQL);
            db.update("hedef", "_id", hedefId, "hedef_yag", yagSQL);


            //Toast.makeText(this,"BMR aktivite olmadan: "+BMR,Toast.LENGTH_LONG).show();


            double kaloriAktivite = 0;

            //aktivite ile birlikte
            if (stringUserAktivite.equals("0")) {
                kaloriAktivite = BMR * 1.2;
            } else if (stringUserAktivite.equals("1")) {
                kaloriAktivite = BMR * 1.375;
            } else if (stringUserAktivite.equals("2")) {
                kaloriAktivite = BMR * 1.55;
            } else if (stringUserAktivite.equals("3")) {
                kaloriAktivite = BMR * 1.725;
            } else if (stringUserAktivite.equals("4")) {
                kaloriAktivite = BMR * 1.9;
            }


            BMR = Math.round(BMR);
            double kaloriAktiviteSQL = db.quoteSmart(kaloriAktivite);


            db.update("hedef", "_id", hedefId, "hedef_kalori_aktiviteİle", kaloriAktiviteSQL);


            double proteinAktivite = Math.round(kaloriAktivite * 25 / 100);
            double karbonhidratAktivite = Math.round(kaloriAktivite * 50 / 100);
            double yagAktivite = Math.round(kaloriAktivite * 25 / 100);

            double proteinAktiviteSQL = db.quoteSmart(proteinAktivite);
            double karbAktiviteSQL = db.quoteSmart(karbonhidratAktivite);
            double yagAktiviteSQL = db.quoteSmart(yagAktivite);

            db.update("hedef", "_id", hedefId, "hedef_protein_aktiviteİle", proteinAktiviteSQL);
            db.update("hedef", "_id", hedefId, "hedef_carb_aktiviteİle", karbAktiviteSQL);
            db.update("hedef", "_id", hedefId, "hedef_yag_aktiviteİle", yagAktiviteSQL);

            //Toast.makeText(this,"BMR aktivite ile : "+BMR,Toast.LENGTH_LONG).show();


            //diyet ile beraber

            double doubleHaftalıkKg = 0;
            try {
                doubleHaftalıkKg = Double.parseDouble(stringHaftalikKg);
            } catch (NumberFormatException nfe) {
                System.out.println("parse edilemedi" + nfe);
            }


            double kalori = 0;
            double kaloriAktiviteVeDiyet = 0;
            kalori = 2800 * doubleHaftalıkKg;

            if (intHedef == 0) {

                kaloriAktiviteVeDiyet = Math.round(BMR - (kalori / 7));

            } else {
                kaloriAktiviteVeDiyet = Math.round(BMR + (kalori / 7));


            }


            double kaloriAktiviteveDiyetSQL = db.quoteSmart(kaloriAktiviteVeDiyet);


            db.update("hedef", "_id", hedefId, "hedef_kalori_aktivite_diyet_ile", kaloriAktiviteveDiyetSQL);

            double proteinAktiviteDiyet = Math.round(kaloriAktiviteVeDiyet * 25 / 100);
            double karbonhidratAktiviteDiyet = Math.round(kaloriAktiviteVeDiyet * 50 / 100);
            double yagAktiviteDiyet = Math.round(kaloriAktiviteVeDiyet * 25 / 100);

            double proteinAktiviteDiyetSQL = db.quoteSmart(proteinAktiviteDiyet);
            double karbAktiviteDiyetSQL = db.quoteSmart(karbonhidratAktiviteDiyet);
            double yagAktiviteDiyetSQL = db.quoteSmart(yagAktiviteDiyet);

            db.update("hedef", "_id", hedefId, "hedef_protein_aktivite_diyet_ile", proteinAktiviteDiyetSQL);
            db.update("hedef", "_id", hedefId, "hedef_carb_aktivite_diyet_ile", karbAktiviteDiyetSQL);
            db.update("hedef", "_id", hedefId, "hedef_yag_aktivite_diyet_ile", yagAktiviteDiyetSQL);


            db.close();


            //}

        /*else {
            textViewErrorMessage.setText(errorMessage);
            textViewErrorMessage.setVisibility(View.VISIBLE);
            imageViewError.setVisibility(View.VISIBLE);
        }*/
        }



    private int getYas(int day, int month, int year) {
        Calendar dogumTarihi = Calendar.getInstance();
        dogumTarihi.set(year, month - 1, day); // Ay değeri 0-11 aralığında olduğundan bir eksiltme yapılır

        Calendar bugun = Calendar.getInstance();

        int yas = bugun.get(Calendar.YEAR) - dogumTarihi.get(Calendar.YEAR);

        // Doğum günü bu yıl gerçekleşmemişse yaş bir azaltılır.
        if (bugun.get(Calendar.MONTH) < dogumTarihi.get(Calendar.MONTH) ||
                (bugun.get(Calendar.MONTH) == dogumTarihi.get(Calendar.MONTH) &&
                        bugun.get(Calendar.DAY_OF_MONTH) < dogumTarihi.get(Calendar.DAY_OF_MONTH))) {
            yas--;
        }

        return yas;
    }



}





