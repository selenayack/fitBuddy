package com.example.fitbuddy;

import android.content.Context;

public class DBSetupInsert {


    private final Context context;

    public DBSetupInsert(Context context) {

        this.context = context;
    }

    public void setupInsertToCategories(String values){
        DbAdapter db = new DbAdapter(context);
        db.open();
        db.insert("categories", "category_id, category_name ,category_parent_id,category_icon,category_note", values);
        //db.close();

    }
    public void insertAllCategories(){

        setupInsertToCategories("NULL,'Ekmek','0','',NULL");
        setupInsertToCategories("NULL,'Meyve','0','',NULL");

    }

    public void setupInsertToFood(String values){
        DbAdapter db = new DbAdapter(context);
        db.open();
        db.insert("food", "besin_id,besin_isim,besin_porsiyon_büyüklügü,besin_porsiyon_ölcüsü,besin_porsiyon_isim_kelime,besin_kalori_hesaplanmıs,besin_protein_hesaplanmıs,besin_yag_hesaplanmıs,besin_karbonhidrat_hesaplanmıs",values);

        //db.close();

    }

    public void insertAllFood(){

        setupInsertToFood("NULL, 'Elma', 90, 'gram', 'g', 52, 0.3, 0.2, 13.4");
        setupInsertToFood("NULL, 'Salatalık', 120, 'gram', 'g', 70, 0.7, 0.8, 13.4");
    }



    }








