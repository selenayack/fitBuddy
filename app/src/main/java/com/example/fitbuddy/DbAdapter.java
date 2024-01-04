package com.example.fitbuddy;// DbAdapter.java

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DbAdapter {



    private static final String DATABASE_NAME = "fitBuddyDiet";
    private static final int DATABASE_VERSION = 56;

    private final Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public DbAdapter(Context ctx) {
        this.context = ctx;
        dbHelper = new DatabaseHelper(context);
    }




    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {

            try {

                db.execSQL("CREATE TABLE IF NOT EXISTS hedef("+
                        "hedef_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        "hedef_mevcut_kilo INT,"+
                        "hedef_mevcut_kilo_tarih DATE, "+
                        "hedef_kilo INT,"+
                        "hedef_haftalık_kilo VARCHAR);");

            }
            catch (SQLException e) {
                e.printStackTrace();
            }


            try {

                db.execSQL("CREATE TABLE IF NOT EXISTS USER("+
                        "user_id TEXT PRIMARY KEY,"+
                        "user_cinsiyet INT,"+
                        "user_boy INT,"+
                        "user_kilo INT,"+
                        "user_olcu VARCHAR,"+
                        "user_aktivite_derecesi INT,"+
                        "user_dogum_tarih DATE," +
                        "user_email TEXT UNIQUE);");

                db.execSQL("CREATE TABLE IF NOT EXISTS food_diary_kalori_yenen("+
                        "kalori_yenen_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        "kalori_yenen_tarih DATE,"+
                        "kalori_yenen_ogun_no INT,"+
                        "kalori_yenen_kalori INT,"+
                        "kalori_yenen_protein INT,"+
                        "kalori_yenen_karbonhidrat INT,"+
                        "kalori_yenen_yag INT);");


                db.execSQL("CREATE TABLE IF NOT EXISTS food_diary("+
                        "fd_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        "fd_tarih DATE,"+
                        "fd_ögün_numara INT,"+
                        "fd_besin_id INT,"+
                        "fd_porsiyon_büyüklügü DOUBLE," +
                        "fd_porsiyon_ölcüsü VARCHAR,"+
                        "fd_kalori_hesaplanmıs DOUBLE," +
                        "fd_protein_hesaplanmıs DOUBLE," +
                        "fd_karbonhidrat_hesaplanmıs DOUBLE," +
                        "fd_yag_hesaplanmıs DOUBLE,"+
                        "fd_ogun_id INT);");

                db.execSQL("CREATE TABLE IF NOT EXISTS categories("+
                        "category_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        "category_name VARCHAR,"+
                        "category_parent_id INT,"+
                        "category_icon VARCHAR,"+
                        "category_note VARCHAR);");


                db.execSQL("CREATE TABLE IF NOT EXISTS food (" +
                        "besin_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "besin_isim VARCHAR, " +
                        "besin_üretici VARCHAR," +
                        "besin_porsiyon_büyüklügü DOUBLE," +
                        "besin_porsiyon_ölcüsü VARCHAR," +
                        "besin_porsiyon_isim_numara DOUBLE," +
                        "besin_porsiyon_isim_kelime VARCHAR," +
                        "besin_kalori DOUBLE," +
                        "besin_karbonhidrat DOUBLE," +
                        "besin_yag DOUBLE," +
                        "besin_protein DOUBLE," +
                        "besin_kalori_hesaplanmıs DOUBLE," +
                        "besin_protein_hesaplanmıs DOUBLE," +
                        "besin_karbonhidrat_hesaplanmıs DOUBLE," +
                        "besin_yag_hesaplanmıs DOUBLE," +
                        "besin_user_id," +
                        "besin_barkod DOUBLE," +
                        "besin_kategori_id INT," +
                        "besin_thumb VARCHAR," +
                        "besin_resim_a VARCHAR," +
                        "besin_resim_b VARCHAR," +
                        "besin_resim_c VARCHAR," +
                        "besin_not VARCHAR);");


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS food");
            db.execSQL("DROP TABLE IF EXISTS categories");
            db.execSQL("DROP TABLE IF EXISTS food_diary");
            db.execSQL("DROP TABLE IF EXISTS food_diary_kalori_yenen");
            db.execSQL("DROP TABLE IF EXISTS user");

            onCreate(db);
        }

    }
    //open database

    public DbAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    //close database
    public void close() {
        dbHelper.close();
    }

    public String quoteSmart(String value){
        boolean isNumeric=false;
        try{
            double myDouble=Double.parseDouble(value);
            isNumeric=true;
        }
        catch (NumberFormatException nfe){
            System.out.println("parse edilemedi"+nfe);
        }

        if(isNumeric=false){

            if(value!=null && value.length()>0){
                value=value.replace("\\","\\\\");
                value=value.replace("'","\\'");
                value=value.replace("\0","\\0");
                value=value.replace("\n","\\n");
                value=value.replace("\r","\\r");
                value=value.replace("\"","\\\"");
                value=value.replace("\\xla","\\z");



            }

        }
        value="'"+value+"'";
        return  value;


    }

    public double quoteSmart(double value){
        return  value;
    }
    public int quoteSmart(int value){
        return  value;
    }




    public void addUserEmail(String email,String userID) {
        ContentValues values = new ContentValues();
        values.put("user_email", email);
        values.put("user_id", userID);

        try {
            db.insert("USER", null, values);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    //veri ekleme
    public void insert(String table,String fields,String values){


            db.execSQL("INSERT INTO " + table + "(" + fields + ") VALUES (" + values + ")");
            //db.close();

    }











    public int count(String table){
        Cursor mcount =db.rawQuery("SELECT COUNT(*) FROM"+table+" ",null);
        mcount.moveToFirst();
        int count=mcount.getInt(0);
        mcount.close();
        return count;
    }


}










