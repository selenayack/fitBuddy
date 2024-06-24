package com.example.fitbuddy;// DbAdapter.java

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public abstract class DbAdapter  extends SQLiteOpenHelper {



    private static final String DATABASE_NAME = "fitBuddyDiet";
    private static final int DATABASE_VERSION = 379;

    private final Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;





    public DbAdapter(Context ctx) {

        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
        dbHelper = new DatabaseHelper(context);
    }

    public Cursor selectAll(String tableName, String[] fields) {
        return db.query(tableName, fields, null, null, null, null, null);
    }

    @SuppressLint("Range")
    public long getUserIdFromFirebaseId(String firebaseUserId) {
        SQLiteDatabase db = this.getReadableDatabase();
         int userId = -1; // Kullanıcının _id'si

        // Veritabanı sorgusu
        String selectQuery = "SELECT _id FROM USER WHERE user_id = ?";

        // Sorguyu çalıştırın ve sonucu alın
        Cursor cursor = db.rawQuery(selectQuery, new String[]{firebaseUserId});

        // Sorgu sonucunu işleyin
        if (cursor.moveToFirst()) {
            // İlgili _id değerini alın
            userId = (int) cursor.getLong(cursor.getColumnIndex("_id"));
        }

        cursor.close(); // Cursor'ı kapatın
        db.close(); // Veritabanı bağlantısını kapatın

        return userId;
    }


    @SuppressLint("Range")
    public float[] getUserData(int userId) {
        Cursor cursor = db.rawQuery(
                "SELECT hedef.hedef_yapılmak_istenen, user.user_cinsiyet, user.user_boy, user.user_kilo, user.user_yas " +
                        "FROM user " +
                        "INNER JOIN hedef ON user._id = hedef._id " +
                        "WHERE user._id=?",
                new String[]{String.valueOf(userId)}
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                float[] userData = new float[5];

                userData[0] = cursor.getFloat(cursor.getColumnIndex("user_cinsiyet"));
                userData[1] = cursor.getFloat(cursor.getColumnIndex("user_yas"));
                userData[2] = cursor.getFloat(cursor.getColumnIndex("user_boy"));
                userData[3] = cursor.getFloat(cursor.getColumnIndex("user_kilo"));
                userData[4] = cursor.getFloat(cursor.getColumnIndex("hedef_yapılmak_istenen"));
                cursor.close();
                return userData;
            }
            cursor.close();
        }
        return null;
    }

    public Cursor select(String tableName, String[] columns) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(tableName, columns, null, null, null, null, null);
    }

    public Cursor getAllData(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + tableName, null);
    }





    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {

            try {

                db.execSQL("CREATE TABLE IF NOT EXISTS hedef("+
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        "hedef_id INTEGER ,"+
                        "hedef_mevcut_kilo DOUBLE,"+
                        "hedef_mevcut_kilo_tarih DATE, "+
                        "hedef_kilo INT,"+
                        "hedef_yapılmak_istenen INT," +
                        "hedef_haftalık_kilo VARCHAR," +
                        "hedef_kalori INT," +
                        "hedef_protein INT," +
                        "hedef_carb INT," +
                        "hedef_yag INT," +
                        "hedef_kalori_aktiviteİle INT," +
                        "hedef_protein_aktiviteİle INT," +
                        "hedef_carb_aktiviteİle INT," +
                        "hedef_yag_aktiviteİle INT," +
                        "hedef_kalori_aktivite_diyet_ile INT," +
                        "hedef_protein_aktivite_diyet_ile INT," +
                        "hedef_carb_aktivite_diyet_ile INT," +
                        "hedef_yag_aktivite_diyet_ile INT," +



                        "hedef_not);");

            }
            catch (SQLException e) {
                e.printStackTrace();
            }


            try {

                db.execSQL("CREATE TABLE IF NOT EXISTS USER("+
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        "user_id TEXT ,"+
                        "user_kilo INT,"+
                        "user_cinsiyet INT,"+
                        "user_boy INT,"+
                        "user_yas INT,"+
                        "user_olcu VARCHAR,"+
                        "user_aktivite_derecesi INT,"+
                        "user_dogum_tarih DATE," +
                        "user_email TEXT UNIQUE );");

                db.execSQL("CREATE TABLE IF NOT EXISTS food_diary_kalori_yenen("+
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        "kalori_yenen_tarih DATE,"+
                        "kalori_yenen_ogun_no INT,"+
                        "kalori_yenen_kalori INT,"+
                        "user_id TEXT ,"+
                        "kalori_yenen_protein INT,"+
                        "kalori_yenen_karbonhidrat INT,"+
                        "kalori_yenen_yag INT);");


                db.execSQL("CREATE TABLE IF NOT EXISTS food_diary("+
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        "fd_id INTEGER,"+
                        "user_id TEXT ,"+
                        "fd_tarih DATE,"+
                        "fd_ögün_numara INT,"+
                        "fd_besin_id INT,"+
                        "fd_porsiyon_büyüklügü_gram DOUBLE," +
                        "fd_porsiyon_büyüklügü_ölcüsü_gram VARCHAR,"+
                        "fd_porsiyon_büyüklügü_adet DOUBLE," +
                        "fd_porsiyon_büyüklügü_ölcüsü_adet VARCHAR,"+
                        "fd_kalori_hesaplanmıs DOUBLE," +
                        "fd_protein_hesaplanmıs DOUBLE," +
                        "fd_karbonhidrat_hesaplanmıs DOUBLE," +
                        "fd_yag_hesaplanmıs DOUBLE,"+
                        "fd_ogun_id INT);");

                db.execSQL("CREATE TABLE IF NOT EXISTS categories("+
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        "category_id INTEGER ,"+
                        "category_name VARCHAR,"+
                        "category_parent_id INT,"+
                        "category_icon VARCHAR,"+
                        "category_note VARCHAR);");


                db.execSQL("CREATE TABLE IF NOT EXISTS food (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "besin_id INTEGER , " +
                        "besin_kategori_id INT," +
                        "besin_isim VARCHAR, " +
                        "besin_aciklama VARCHAR, " +
                        "besin_üretici VARCHAR," +
                        "besin_porsiyon_büyüklügü_gram DOUBLE," +
                        "besin_porsiyon_büyüklügü_ölcüsü_gram VARCHAR," +
                        "besin_porsiyon_büyüklügü_adet DOUBLE," +
                        "besin_porsiyon_büyüklügü_adet_ölcüsü VARCHAR," +
                        "besin_kalori DOUBLE," +
                        "besin_karbonhidrat DOUBLE," +
                        "besin_yag DOUBLE," +
                        "besin_protein DOUBLE," +
                        "besin_kalori_hesaplanmıs DOUBLE," +
                        "besin_protein_hesaplanmıs DOUBLE," +
                        "besin_karbonhidrat_hesaplanmıs DOUBLE," +
                        "besin_yag_hesaplanmıs DOUBLE," +
                        "besin_user_id TEXT," +
                        "besin_barkod DOUBLE," +
                        "besin_thumb VARCHAR," +
                        "besin_resim_a VARCHAR," +
                        "besin_resim_b VARCHAR," +
                        "besin_resim_c VARCHAR," +
                        "besin_son_kullanılan DATE," +
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
            db.execSQL("DROP TABLE IF EXISTS hedef");


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
    public String quoteSmartForDate(String value) {
        return value; // Tarih formatı zaten 'dd-MM-yyyy' şeklinde olduğu için değişiklik yapmıyoruz
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
    @SuppressLint("Range")
    public long getSQLiteIdFromFirebaseId(String firebaseUserId) {
        SQLiteDatabase db = this.getReadableDatabase();
        long sqliteUserId = -1;

        String selectQuery = "SELECT _id FROM USER WHERE user_id = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{firebaseUserId});

        if (cursor.moveToFirst()) {
            sqliteUserId = cursor.getLong(cursor.getColumnIndex("_id"));
        }

        cursor.close();
        db.close();

        return sqliteUserId;
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

    private void addDefaultGoalForUser(long userId) {

        ContentValues goalValues = new ContentValues();
        goalValues.put("hedef_id", userId);  // Kullanıcının _id'si ile hedefi ilişkilendir


        try {
            db.insert("hedef", null, goalValues);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //veri ekleme
    public void insert(String table,String fields,String values){


            db.execSQL("INSERT INTO " + table + "(" + fields + ") VALUES (" + values + ")");
            //db.close();

    }

    public void insert(String table, ContentValues values) {
        db.insert(table, null, values);
    }

    public Cursor select(String tableName, String[] columns, String whereClause) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(tableName, columns, whereClause, null, null, null, null);
    }

    public Cursor select(String tableName, String[] fields, String[] whereClause, String[] whereCondition, String[] whereAndOr) throws SQLException {
        String where="";
        int arraySize = whereClause.length;

        for (int x = 0; x < arraySize; x++) {
            if (where.equals("")) {
                where = whereClause[x] + "='" + whereCondition[x] + "'";

            } else {
                where = where + " " + whereAndOr[x - 1] + " " + whereClause[x] + "=" + whereCondition[x] ;
            }
        }
        Toast.makeText(context, where, Toast.LENGTH_SHORT).show();

        Cursor mcursor = db.query(tableName, fields, where,null, null, null, null, null);

        if (mcursor != null) {
            mcursor.moveToFirst();
        }
        return mcursor;
    }




    public Cursor select(String table, String[] columns, String whereColumn, String whereValue) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = whereColumn + " = ?";
        String[] selectionArgs = { whereValue };
        Cursor cursor = db.query(table, columns, selection, selectionArgs, null, null, null);
        return cursor;
    }


    public Cursor select(String tableName, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy);
    }


    public Cursor select(String table, String[]fields, String whereClause,String whereCondition,String orderBy,String OrderMethod){

        Cursor mCursor=null;

        if(whereClause==""){
            mCursor=db.query(table,fields,null,null,null,null,orderBy+" "+OrderMethod);

        }
        else{
            mCursor=db.query(table,fields,whereClause+"="+whereCondition,null,null,null,orderBy+" "+OrderMethod);

        }


        if(mCursor!=null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }




    public int count(String table) {
        try {
            Cursor mcount = db.rawQuery("SELECT COUNT(*) FROM " + table, null);
            mcount.moveToFirst();
            int count = mcount.getInt(0);
            mcount.close();
            return count;
        } catch (SQLException e) {
            return -1;
        }
    }


    public Cursor selectPrimaryKey(String table,String primaryKey,long sutunId,String []fields)throws SQLException{

   // Cursor cursor = db.query(table, fields, primaryKey + "=", new String[]{String.valueOf(sutunId)}, null, null, null);
    Cursor cursor = db.query(table, fields, primaryKey + "="+ sutunId, null, null, null,null);
    if (cursor != null) {
        cursor.moveToFirst();
    }

    return cursor;
}


        public boolean update(String table, String primaryKey, long sutunId, String fields[], String value[]) throws SQLException {
            ContentValues args = new ContentValues();
            for (int i = 0; i < fields.length; i++) {

                value[i]=  value[i].substring(1,value[i].length()-1);
                args.put(fields[i], value[i]);
            }
            return db.update(table, args, primaryKey + "=" + sutunId, null) > 0;
        }











    public boolean update(String table,String primaryKey,long sutunId,String fields,String value)throws SQLException{

        value=value.substring(1,value.length()-1);

        ContentValues args=new ContentValues();
       args.put(fields,value);
       return db.update(table,args,primaryKey+"="+sutunId,null)>0;


    }

    public boolean update(String table,String primaryKey,long sutunId,String fields,double value)throws SQLException{


        ContentValues args=new ContentValues();
        args.put(fields,value);
        return db.update(table,args,primaryKey+"="+sutunId,null)>0;



    }

    public boolean update(String table,String primaryKey,long sutunId,String fields,int value)throws SQLException{


        ContentValues args=new ContentValues();
        args.put(fields,value);
        return db.update(table,args,primaryKey+"="+sutunId,null)>0;



    }

    public boolean userExists(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT 1 FROM USER WHERE user_id = ?";
            cursor = db.rawQuery(query, new String[]{userId});
            return cursor != null && cursor.moveToFirst();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public boolean updateUser(String table, ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();

            // Eğer kullanıcı zaten varsa, kullanıcı bilgilerini güncelle
            if (userExists(values.getAsString("user_id"))) {
                ContentValues updatedValues = new ContentValues();

                if (values.containsKey("user_dogum_tarih")) {
                    updatedValues.put("user_dogum_tarih", values.getAsString("user_dogum_tarih"));
                }

                if (values.containsKey("user_cinsiyet")) {
                    updatedValues.put("user_cinsiyet", values.getAsString("user_cinsiyet"));
                }

                if (values.containsKey("user_boy")) {
                    updatedValues.put("user_boy", values.getAsInteger("user_boy"));
                }

                if (values.containsKey("user_kilo")) {
                    updatedValues.put("user_kilo", values.getAsInteger("user_kilo"));
                }
                if (values.containsKey("user_yas")) {
                    updatedValues.put("user_yas", values.getAsInteger("user_yas"));
                }

                if (values.containsKey("user_aktivite_derecesi")) {
                    updatedValues.put("user_aktivite_derecesi", values.getAsString("user_aktivite_derecesi"));
                }



                if (values.containsKey("user_olcu")) {
                    updatedValues.put("user_olcu", values.getAsString("user_olcu"));
                }

                if (values.containsKey("user_email")) {
                    updatedValues.put("user_email", values.getAsString("user_email"));
                }

                int updatedRows = db.update(table, updatedValues, whereClause, whereArgs);
                return updatedRows > 0;
            } else {
                // Eğer kullanıcı yoksa, yeni bir kullanıcı ekleyin
                ContentValues userValues = new ContentValues();
                userValues.put("user_id", values.getAsString("user_id"));
                userValues.put("user_dogum_tarih", values.getAsString("user_dogum_tarih"));
                userValues.put("user_cinsiyet", values.getAsString("user_cinsiyet"));
                userValues.put("user_boy", values.getAsInteger("user_boy"));
                userValues.put("user_kilo", values.getAsInteger("user_kilo"));
                userValues.put("user_olcu", values.getAsString("user_olcu"));
                userValues.put("user_email", values.getAsString("user_email"));
                userValues.put("user_aktivite_derecesi", values.getAsString("user_aktivite_derecesi"));


                long newRowId = db.insert(table, null, userValues);
                return newRowId != -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    public void delete(String tableName, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, whereClause, whereArgs);
        db.close();
    }








}









