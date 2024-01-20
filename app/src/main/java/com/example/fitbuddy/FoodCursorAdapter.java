package com.example.fitbuddy;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.fitbuddy.R;

import org.w3c.dom.Text;

public class FoodCursorAdapter extends CursorAdapter {

    public FoodCursorAdapter(Context context, Cursor c) {
        super(context, c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.fragment_food_list_items,viewGroup,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textViewListName = (TextView) view.findViewById(R.id.textViewListName);
        TextView textViewListNumber=(TextView)view.findViewById(R.id.textViewListNumber);

        TextView textViewListSubName=(TextView)view.findViewById(R.id.textViewListSubName);





        String getFoodServingMesurment = cursor.getString(cursor.getColumnIndexOrThrow("besin_porsiyon_büyüklügü_ölcüsü_gram"));
        String getFoodServingSize = cursor.getString(cursor.getColumnIndexOrThrow("besin_porsiyon_büyüklügü_gram"));
        int getFoodEnergyCalculated = cursor.getInt(cursor.getColumnIndexOrThrow("besin_kalori_hesaplanmıs"));
        String getFoodporsiyonİsimKelime = cursor.getString(cursor.getColumnIndexOrThrow("besin_porsiyon_büyüklügü_adet_ölcüsü"));
        //double getFoodporsiyonİsimNumara = cursor.getDouble(cursor.getColumnIndexOrThrow("besin_porsiyon_isim_numara"));




        int getId=cursor.getInt(cursor.getColumnIndexOrThrow("_id"));

        String getName=cursor.getString(cursor.getColumnIndexOrThrow("besin_isim"));

        textViewListName.setText(getName);
        textViewListNumber.setText(String.valueOf(getFoodEnergyCalculated)+" "+"kcal");
        textViewListSubName.setText(getFoodServingSize+" "+getFoodServingMesurment+"("+"1"+" "+getFoodporsiyonİsimKelime+")");

    }
}
