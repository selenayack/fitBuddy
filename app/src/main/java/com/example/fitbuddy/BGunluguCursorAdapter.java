package com.example.fitbuddy;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class BGunluguCursorAdapter extends CursorAdapter {

    public BGunluguCursorAdapter(Context context, Cursor c) {
        super(context, c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.fragment_list_bgunlugu,viewGroup,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textViewListTarih = (TextView) view.findViewById(R.id.textViewListTarih);
        TextView textViewListKalori=(TextView)view.findViewById(R.id.textViewListToplamKalori);

        TextView textViewListProtein=(TextView)view.findViewById(R.id.textViewListProtein);
        TextView textViewListYag=(TextView)view.findViewById(R.id.textViewListYag);
        TextView textViewListKarb=(TextView)view.findViewById(R.id.textViewListKarb);



        int getEnergy= cursor.getInt(cursor.getColumnIndexOrThrow("kalori_yenen_kalori"));
        String tarih=cursor.getString(cursor.getColumnIndexOrThrow("kalori_yenen_tarih"));
        int getP= cursor.getInt(cursor.getColumnIndexOrThrow("kalori_yenen_protein"));
        int getK= cursor.getInt(cursor.getColumnIndexOrThrow("kalori_yenen_karbonhidrat"));
        int getY= cursor.getInt(cursor.getColumnIndexOrThrow("kalori_yenen_yag"));



        textViewListTarih.setText(tarih);
        textViewListKalori.setText(String.valueOf(getEnergy)+" "+"kcal");
        textViewListProtein.setText(String.valueOf(getP)+" "+"g protein");
        textViewListKarb.setText(String.valueOf(getK)+" "+"g karbonhidrat");
        textViewListYag.setText(String.valueOf(getY)+" "+"g yağ");




    }
}

