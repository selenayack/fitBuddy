package com.example.fitbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class user_infos extends AppCompatActivity {

    private String[] arraySpinnerDTGün;
    private String[] arraySpinnerDTYıl=new String[101];




    private DbAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfos);






        this.arraySpinnerDTGün=new String[]{
                "1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"
        };
        Spinner spinnerDTGün =(Spinner) findViewById(R.id.spinner4);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arraySpinnerDTGün);
        spinnerDTGün.setAdapter(adapter);


        Calendar calendar=Calendar.getInstance();
        int year =calendar.get(Calendar.YEAR);
        int end=year-100;
        int index=0;

        for(int x=year;x>end;x--){
            this.arraySpinnerDTYıl[index]=""+x;
            index++;
        }

        Spinner spinnerDTYıl=(Spinner) findViewById(R.id.spinner5);
        ArrayAdapter<String> adapterYear = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arraySpinnerDTYıl);
        spinnerDTYıl.setAdapter(adapterYear);

        Button buttonDevam=(Button) findViewById(R.id.buttonBilgiGuncelle);
        buttonDevam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                devamEt();

            }
        });

        TextView textViewErrorMessage=(TextView) findViewById(R.id.textViewErrorMessage);
        textViewErrorMessage.setVisibility(View.GONE);

        ImageView imageViewError=(ImageView)findViewById(R.id.imageViewerrormessage);
        imageViewError.setVisibility(View.GONE);



        EditText editTextHeightInches=(EditText)findViewById(R.id.editTextHeightInches);
        editTextHeightInches.setVisibility(View.GONE);


        Spinner spinnerOlcuBirimi=(Spinner) findViewById(R.id.spinnerHaftalıkKg);


        spinnerOlcuBirimi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long l) {
                olcuDegistir();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //olcuDegistir();

            }
        });



    }

    private void olcuDegistir() {

        Spinner spinnerOlcuBirimi=(Spinner) findViewById(R.id.spinnerHaftalıkKg);
        String stringOlcuBiirimi=spinnerOlcuBirimi.getSelectedItem().toString();

        EditText editTextHeightCm=(EditText) findViewById(R.id.editTextHeightCm);
        EditText editTextHeightInches=(EditText)findViewById(R.id.editTextHeightInches);
        String stringHeightCm=editTextHeightCm.getText().toString();
        String stringHeightInches=editTextHeightInches.getText().toString();

        double heightCm=0;
        double heightFeet=0;
        double heightInches=0;

        TextView textViewCm=(TextView)findViewById(R.id.textViewCm);
        TextView textViewKg=(TextView)findViewById(R.id.textViewOlcuBirimi2);
        if(stringOlcuBiirimi.startsWith("p")){
            editTextHeightInches.setVisibility(View.VISIBLE);
            textViewCm.setText("feet and inches");
            textViewKg.setText("pound");
            try {
                heightCm=Double.parseDouble(stringHeightCm);
            }
            catch (NumberFormatException nfe){

            }
            if(heightCm!=0){
                heightFeet=(heightCm*0.3937008)/12;
                int intHeightFeet=(int) heightFeet;
                editTextHeightCm.setText(""+intHeightFeet);
            }


        }
        else{
            editTextHeightInches.setVisibility(View.GONE);
            textViewCm.setText("cm");
            textViewKg.setText("kg");
            try {
                heightFeet=Double.parseDouble(stringHeightCm);
            }
            catch (NumberFormatException nfe) {
            }


            try {
                heightInches=Double.parseDouble(stringHeightInches);
            }
            catch (NumberFormatException nfe){

            }

            if(heightFeet!=0 && heightInches!=0) {
                heightCm = ((heightFeet * 12) + heightInches) * 2.54;
                heightCm=Math.round(heightCm);
                editTextHeightCm.setText(""+heightCm);
            }

        }

        EditText editTextWeight=(EditText) findViewById(R.id.editTextWeight);
        String stringWeight=editTextWeight.getText().toString();
        double doubleWeight=0;
        try {
            doubleWeight=Double.parseDouble(stringWeight);
        }
        catch (NumberFormatException nfe) {

        }

        if(doubleWeight!=0) {
            if (stringOlcuBiirimi.startsWith("p")) {

                doubleWeight = Math.round(doubleWeight / 0.45359237);

            } else {
                doubleWeight = Math.round(doubleWeight * 0.45359237);

            }
            editTextWeight.setText("" + doubleWeight);

        }



    }

    private void devamEt() {


        TextView textViewErrorMessage = (TextView) findViewById(R.id.textViewErrorMessage);
        ImageView imageViewError = (ImageView) findViewById(R.id.imageViewerrormessage);

        String errorMessage = "";

        Spinner spinnerDTGün = (Spinner) findViewById(R.id.spinner4);
        String stringDTGün = spinnerDTGün.getSelectedItem().toString();
        int DTGün = 0;
        try {
            DTGün = Integer.parseInt(stringDTGün);
            if (DTGün < 10) {
                stringDTGün = "0" + stringDTGün;
            }
        } catch (NumberFormatException nfe) {
            System.out.println("parse ediemedi" + nfe);
            errorMessage = "lütfen dogum gününüzü girin";


        }

        Spinner spinnerDTAy = (Spinner) findViewById(R.id.spinner);
        String stringDTAy = spinnerDTAy.getSelectedItem().toString();
        if (stringDTAy.startsWith("Oc")) {
            stringDTAy = "01";
        } else if (stringDTAy.startsWith("Şu")) {
            stringDTAy = "02";
        } else if (stringDTAy.startsWith("Mar")) {

            stringDTAy = "03";
        } else if (stringDTAy.startsWith("Ni")) {
            stringDTAy = "04";
        } else if (stringDTAy.startsWith("May")) {
            stringDTAy = "05";
        } else if (stringDTAy.startsWith("Haz")) {
            stringDTAy = "06";
        } else if (stringDTAy.startsWith("Tem")) {
            stringDTAy = "07";
        } else if (stringDTAy.startsWith("Ağ")) {
            stringDTAy = "08";
        } else if (stringDTAy.startsWith("Ey")) {
            stringDTAy = "09";
        } else if (stringDTAy.startsWith("Ek")) {
            stringDTAy = "10";
        } else if (stringDTAy.startsWith("Ka")) {
            stringDTAy = "11";
        } else if (stringDTAy.startsWith("Ar")) {
            stringDTAy = "12";
        }


        Spinner spinnerDTYıl = (Spinner) findViewById(R.id.spinner5);
        String stringDTYıl = spinnerDTYıl.getSelectedItem().toString();
        int DTYıl = 0;
        try {
            DTYıl = Integer.parseInt(stringDTYıl);
        } catch (NumberFormatException nfe) {
            System.out.println("parse edilemedi" + nfe);
            errorMessage = "lütfen dogum yilinizi girin";


        }

        String dogumTarihi = stringDTGün + "-" + stringDTAy + "-" + DTYıl;
        //Toast.makeText(this,dogumTarihi,Toast.LENGTH_SHORT).show();


        RadioGroup radioGroupCinsiyet = (RadioGroup) findViewById(R.id.radioGrupCinsiyet);
        int selectedId = radioGroupCinsiyet.getCheckedRadioButtonId();
        RadioButton radioButtonCinsiyet = (RadioButton) findViewById(selectedId);

        if (radioGroupCinsiyet.getCheckedRadioButtonId() == -1) {
            // Cinsiyet seçilmemişse
            String errorMessageGender = "Cinsiyet seçiniz."; // Hata mesajı
            textViewErrorMessage.setText(errorMessageGender);
            textViewErrorMessage.setVisibility(View.VISIBLE);
            imageViewError.setVisibility(View.VISIBLE);
            return; // Fonksiyonun burada sonlanması
        }



        String stringGender = radioButtonCinsiyet.getText().toString();
        int genderValue;

        if (stringGender.equals("Kadın")) {
            genderValue = 0;
        } else if (stringGender.equals("Erkek")) {
            genderValue = 1;
        } else {
            // Belirlenmemiş bir değer varsa bir hata mesajı gösterilebilir.
            String errorMessageGender = "Geçersiz cinsiyet seçimi.";
            textViewErrorMessage.setText(errorMessageGender);
            textViewErrorMessage.setVisibility(View.VISIBLE);
            imageViewError.setVisibility(View.VISIBLE);
            return;
        }




        //Toast.makeText(this,radioButtonCinsiyet.getText(),Toast.LENGTH_SHORT).show();


        EditText editTextHeightInches = (EditText) findViewById(R.id.editTextHeightInches);
        EditText editTextHeightCm = (EditText) findViewById(R.id.editTextHeightCm);
        String stringHeightCm = editTextHeightCm.getText().toString();
        String stringHeightInches = editTextHeightInches.getText().toString();

        double heightCm = 0;
        double heightFeet = 0;
        double heightInches = 0;
        boolean metric = true;

        Spinner spinnerOlcuBirimi = (Spinner) findViewById(R.id.spinnerHaftalıkKg);
        String stringOlcuBiirimi = spinnerOlcuBirimi.getSelectedItem().toString();

        int intOlcu = spinnerOlcuBirimi.getSelectedItemPosition();

        if (intOlcu == 0) {
            stringOlcuBiirimi = "kg_cm";


        } else {
            stringOlcuBiirimi = "pound_inch";
            metric = false;


        }

        if (stringOlcuBiirimi.startsWith("p")) {
            metric = false;
        }

        if (metric == true) {
            try {
                heightCm = Double.parseDouble(stringHeightCm);
            } catch (NumberFormatException nfe) {
                errorMessage = "Boy(cm) sayı olmalı";
            }

        } else {

            try {
                heightFeet = Double.parseDouble(stringHeightCm);
            } catch (NumberFormatException nfe) {
                errorMessage = "Boy(feet) sayı olmalı";
            }
            try {
                heightInches = Double.parseDouble(stringHeightInches);
            } catch (NumberFormatException nfe) {
                errorMessage = "Boy(inch) sayı olmalı";
            }

            heightCm = ((heightFeet * 12) + heightInches) * 2.54;
            heightCm = Math.round(heightCm);

        }


        EditText editTextWeight = (EditText) findViewById(R.id.editTextWeight);
        String stringWeight = editTextWeight.getText().toString();
        double doubleWeight = 0;

        if (stringOlcuBiirimi.startsWith("p")) {
            metric = false;
        }

        if (metric == true) {
            try {
                doubleWeight = Double.parseDouble(stringWeight);
            } catch (NumberFormatException nfe) {
                errorMessage = "Kilo sayı olmalı";
            }

        } else {


            doubleWeight = Math.round(doubleWeight * 0.45359237);

        }

        editTextWeight.setText("" + doubleWeight);








       /* try {
            doubleWeight=Double.parseDouble(stringWeight);
        }
        catch (NumberFormatException nfe) {
            errorMessage="kilo sayı olmalı";

        }

        if(stringOlcuBiirimi.startsWith("p")) {

            doubleWeight = Math.round(doubleWeight / 0.45359237);
        }
        else{
            doubleWeight = Math.round(doubleWeight *0.45359237);

        }

        editTextWeight.setText("" +doubleWeight);*/


        Spinner spinnerAktiviteSeviyesi = (Spinner) findViewById(R.id.spinner3);

        int intActivityLevel = spinnerAktiviteSeviyesi.getSelectedItemPosition();


        if (errorMessage.isEmpty()) {
            textViewErrorMessage.setVisibility(View.GONE);
            imageViewError.setVisibility(View.GONE);


            DbAdapter db = new DbAdapter(this) {
                @Override
                public void onCreate(SQLiteDatabase sqLiteDatabase) {

                }

                @Override
                public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

                }
            };
            db.open();

            EditText editTextYas = (EditText) findViewById(R.id.editTextYas);
            String stringYas = editTextYas.getText().toString();

            String dogumTarihiSQL = db.quoteSmart(dogumTarihi);
            String stringCinsiyetSQL = String.valueOf(db.quoteSmart(genderValue));
            double heightCmSQL = db.quoteSmart(heightCm);
            int intActivityLevelSQL = db.quoteSmart(intActivityLevel);
            String doubleWeightSQL = db.quoteSmart(stringWeight);
            String yasSql = db.quoteSmart(stringYas);
            String stringOlcuSQL = db.quoteSmart(stringOlcuBiirimi);


            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                String email = user.getEmail();
                String userID = user.getUid();
                String stringInput = "NULL,'" + userID+"',"+ dogumTarihiSQL + "," + doubleWeightSQL+","+ stringCinsiyetSQL + "," + heightCmSQL + "," +stringYas+","+ intActivityLevelSQL + ",'" + email + "'," + stringOlcuSQL;

                db.insert("USER", "_id,user_id,user_dogum_tarih,user_kilo,user_cinsiyet,user_boy,user_yas,user_aktivite_derecesi,user_email,user_olcu", stringInput);

                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                String hedefTarih = df.format(Calendar.getInstance().getTime());



                /*Calendar cc=Calendar.getInstance();
                int yıl=cc.get(Calendar.YEAR);
                int ay=cc.get(Calendar.MONTH);
                int gün=cc.get(Calendar.DAY_OF_MONTH);
                String hedefTarih=gün+"-"+ay+"-"+yıl;*/
                String hedefTarihSQL = db.quoteSmart(hedefTarih);


                stringInput = "NULL," + doubleWeightSQL + "," + hedefTarihSQL;

                db.insert("hedef", "_id,hedef_mevcut_kilo,hedef_mevcut_kilo_tarih", stringInput);





                Intent i = new Intent(user_infos.this, calculate_bmr.class);
                startActivity(i);
            }

        } else {
            textViewErrorMessage.setText(errorMessage);
            textViewErrorMessage.setText(errorMessage);
            textViewErrorMessage.setVisibility(View.VISIBLE);
            imageViewError.setVisibility(View.VISIBLE);
        }


    }}


