package com.example.fitbuddy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBSetupInsert {


    private final Context context;

    public DBSetupInsert(Context context) {

        this.context = context;
    }

    public void setupInsertToCategories(String values){
        DbAdapter db = new DbAdapter(context) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };
        db.open();
        db.insert("categories", "_id, category_name ,category_parent_id,category_icon,category_note", values);
        //db.close();

    }
    public void insertAllCategories(){


        setupInsertToCategories("NULL,'Ekmek','0','',NULL");
        setupInsertToCategories("NULL,'Beyaz Ekmek','1','',NULL");
        setupInsertToCategories("NULL,'Tam Buğday Ekmek','1','',NULL");
        setupInsertToCategories("NULL,'Mısır Gevreği','1','',NULL");
        setupInsertToCategories("NULL,'Çavdar Ekmeği','1','',NULL");
        setupInsertToCategories("NULL,'Atıştırmalık','0','',NULL");
        setupInsertToCategories("NULL,'Bisküvi','6','',NULL");
        setupInsertToCategories("NULL,'İçecekler','0','',NULL");
        setupInsertToCategories("NULL,'Kahve','8','',NULL");
        setupInsertToCategories("NULL,'Çay','8','',NULL");
        setupInsertToCategories("NULL,'Kola','8','',NULL");
        setupInsertToCategories("NULL,'Sebze','0','',NULL");
        setupInsertToCategories("NULL,'Meyve','0','',NULL");
        setupInsertToCategories("NULL,'Et','0','',NULL");
        setupInsertToCategories("NULL,'Tavuk','0','',NULL");
        setupInsertToCategories("NULL,'Yumurta','0','',NULL");
        setupInsertToCategories("NULL,'Yoğurt','0','',NULL");
        setupInsertToCategories("NULL,'Süt','0','',NULL");
        setupInsertToCategories("NULL,'Peynir','0','',NULL");
        setupInsertToCategories("NULL,'Kaşar peynir','19','',NULL");
        setupInsertToCategories("NULL,'Beyaz peynir','19','',NULL");
        setupInsertToCategories("NULL,'Zeytin','0','',NULL");
        setupInsertToCategories("NULL,'Makarna','0','',NULL");
        setupInsertToCategories("NULL,'Pizza','0','',NULL");
        setupInsertToCategories("NULL,'Salata','0','',NULL");
        setupInsertToCategories("NULL,'Sebze yemeği','0','',NULL");
        setupInsertToCategories("NULL,'Pilav','0','',NULL");
        setupInsertToCategories("NULL,'Çorba','0','',NULL");











    }

    public void setupInsertToFood(String values){
        DbAdapter db = new DbAdapter(context) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };
        db.open();
        db.insert("food", "_id,besin_kategori_id ,besin_isim,besin_porsiyon_büyüklügü_gram,besin_porsiyon_büyüklügü_ölcüsü_gram,besin_porsiyon_büyüklügü_adet,besin_porsiyon_büyüklügü_adet_ölcüsü,besin_kalori,besin_protein,besin_yag,besin_karbonhidrat,besin_kalori_hesaplanmıs,besin_protein_hesaplanmıs,besin_yag_hesaplanmıs,besin_karbonhidrat_hesaplanmıs",values);

        db.close();

    }



    public void insertAllFood(){


        setupInsertToFood("NULL, 13,'Elma', 90, 'gram',1,'adet',52, 0.26,0.17,13.81,47,0.23,0.15,12.43");
        setupInsertToFood("NULL, 12,'Salatalık', 120, 'gram', 1,'adet',10,0.7,0.2,2.12,14,0.74,0.24,2.17");
        setupInsertToFood("NULL, 16,'Haşlanmış Yumurta',50,'gram',1,'adet', 158, 12.1, 11.2,1.2, 78,6.29,5.31,0.56");
        setupInsertToFood("NULL, 20,'Kaşar Peynir', 20,'gram',1,'dilim', 355,27,26,2.56,71,5.4,5.32,0.51");
        setupInsertToFood("NULL, 21,'Beyaz Peynir', 30,'gram',1,'dilim', 289,22.5,21.6,0,93,6.11,7.29,0.76");
        setupInsertToFood("NULL, 22,'Siyah Zeytin',23,'gram',1,'adet', 207,01.8,0.2,2.0,27,0.19,2.51,1.39");
        setupInsertToFood("NULL, 18,'Süt', 200, 'gram', 1,'su bardağı',61,3.32,03.3,04.7,124,6.66,6.66,10.84");
        setupInsertToFood("NULL,23, 'Makarna salçalı', 194, 'gram',1,'tabak',115,3.47,0.99,24.82,231,6.7,1.7,47.9 ");
        setupInsertToFood("NULL,26, 'Taze Fasulye', 150, 'gram',1,'tabak', 31,1.8,0.2,7,66,3.3,0.6,10.5");
        setupInsertToFood("NULL, 24,'Karışık Pizza', 130, 'gram',1,'dilim', 186,5.84,4.33,30.51,241,7.59,5.63,39.66");
        setupInsertToFood("NULL, 15,'Tavuk sote', 170, 'gram',1,'tabak', 169,16.74,8.94,4.51,288,28.46,15.2,7.6");
        setupInsertToFood("NULL,27, 'Pilav', 105, 'gram',1,'tabak',130,2.38,0.21,28.59,154,2.61,2.61,29.68");
        setupInsertToFood("NULL, 2,'Beyaz Ekmek', 25, 'gram',1,'dilim',264,9,3.2,49,60,2.67,0.54,10.8");


        setupInsertToFood("NULL, 28,'Mercimek çorbası', 275 ,'gram',1,'kase',55,1.37,1.18,3.26,85,3.75,3.25,8.98");
        setupInsertToFood("NULL, 25,'Tavuklu salata', 200 ,'gram',1,'kase',65, 9.7,1.34,3.24,130,19.46,2.68,6.47");
        setupInsertToFood("NULL,27, 'Bulgur pilavı', 130, 'gram', 1,'tabak',120, 2.78,3.39 ,18.19,149,3.61,4.4,23.64");
        setupInsertToFood("NULL, 14,'Et kavurma', 150, 'gram',1,'tabak', 45, 20.62,28.53,0,360,25.95,27.03,0");







    }



    }








