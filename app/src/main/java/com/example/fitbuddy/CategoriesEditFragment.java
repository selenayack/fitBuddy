package com.example.fitbuddy;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;


public class CategoriesEditFragment extends Fragment {

    private View mainView;
    private String currentName;
    private String currentId;
    Cursor categoriesCursor;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.fragment_categories_edit, container, false);

        return mainView;


    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        populateList("0","");
        EditKategori();




    }

    @SuppressLint("Range")
    private void populateList(String parentId,String parentName) {


        DbAdapter db= new DbAdapter(getActivity()) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };
        db.open();

        String fields[]=new String[]{
                "_id",
                "category_name",
                "category_parent_id"
        };

        categoriesCursor=db.select("categories",fields,"category_parent_id",parentId, "category_name","ASC");

        ArrayList<String> values = new ArrayList<String>();



        int categoriesCount=categoriesCursor.getCount();




        if (categoriesCursor.moveToFirst()) {
            for (int i = 0; i < categoriesCount; i++) {

                values.add(categoriesCursor.getString(categoriesCursor.getColumnIndex("category_name")));

               /* Toast.makeText(getActivity(), "Id:" + categoriesCursor.getString(0) + "\n" +
                                "İsim:" + categoriesCursor.getString(1),
                        Toast.LENGTH_SHORT).show();*/



                categoriesCursor.moveToNext();
            }

        }

        //categoriesCursor.close();

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,values);

        ListView lv=(ListView)getActivity().findViewById(R.id.listviewKategori);
        lv.setAdapter(adapter);



        if(parentId.equals("0")){
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    listİtemClicked(arg2);
                }
            });}

        db.close();



    }

    private void listİtemClicked(int listItemIdClicked) {
        //Toast.makeText(getActivity(),"ID"+listItemIdClicked, Toast.LENGTH_SHORT).show();

        categoriesCursor.moveToPosition(listItemIdClicked);

        String id=categoriesCursor.getString(0);
        String name=categoriesCursor.getString(1);
        String parentId=categoriesCursor.getString(2);
        ((MainActivity2)getActivity()).getSupportActionBar().setTitle(name);
        populateList(id,name);



        /*Toast.makeText(getActivity(), "Id:" + categoriesCursor.getString(0) + "\n" +
                        "İsim:" + categoriesCursor.getString(1),
                Toast.LENGTH_SHORT).show();*/
    }




    private void EditKategori() {

        DbAdapter db = new DbAdapter(getActivity()) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };
        db.open();



        String fields[] = new String[]{
                "_id",
                "category_name",
                "category_parent_id"
        };

        Cursor categoriesCursor = db.select("categories", fields, "category_parent_id", "0","category_name","ASC");
        int dbCursorCount = categoriesCursor.getCount();
        String[] arraySpinerKategori = new String[dbCursorCount+1];
        arraySpinerKategori[0]="-";

        if (categoriesCursor.moveToFirst()) {
            for (int x = 1; x < dbCursorCount+1; x++) {
                arraySpinerKategori[x] = categoriesCursor.getString(1).toString();
                Log.d("CREATE_KATEGORI", "Veri Çekildi: " + arraySpinerKategori[x]);
                categoriesCursor.moveToNext();
            }
            // Cursor'ı kapat

        } else {
            Log.d("CREATE_KATEGORI", "Veri Alınamadı");
        }

        categoriesCursor.close();

        Spinner spinnerParent = getActivity().findViewById(R.id.spinnerParent);

        if (spinnerParent != null) {

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_item, arraySpinerKategori);

            spinnerParent.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            spinnerParent.setVisibility(View.VISIBLE);
        } else {
            Log.e("CategoriesAdd", "spinnerParent null. Uygun ID kullanıldığından emin olun.");
            // veya gerekirse kullanıcıya bir hata mesajı gönderin.
        }



    }


}