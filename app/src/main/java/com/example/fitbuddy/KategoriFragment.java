package com.example.fitbuddy;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import java.security.Provider;
import java.util.ArrayList;

public class KategoriFragment extends Fragment {

    private Provider menuProvider;

    Cursor categoriesCursor;
    DbAdapter db;

    private View mainView;
    private ListView lv;

    private String[] arraySpinnerCategories;






    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        populateList("0","");
        //noinspection deprecation


    }




    /** @noinspection deprecation*/


       /* @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.action_plus) {
                // Add the necessary operations to be performed when clicked
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_LONG).show();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }



    /**
     * @param menu     The options menu in which you place your items.
     * @param inflater

     * @noinspection deprecation
     */

   /* @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.categories_menu, menu);
        MenuItem actionPlusItem = menu.findItem(R.id.action_plus);
        actionPlusItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_LONG).show();
                // Diğer işlemleri burada yapabilirsiniz
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    */






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

    @SuppressLint("Range")
    private void populateListSub(String parentId, String parentName) {
        DbAdapter db = new DbAdapter(getActivity()) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        }; // veya gerekli context




        db.open();

        String fields[]=new String[]{
                "_id",
                "category_name"
        };

        Cursor subCursor=db.select("categories",fields,"category_parent_id",parentId);

        ArrayList<String> values = new ArrayList<>();



        int cursorCount=subCursor.getCount();




        if (subCursor.moveToFirst()) {
            for (int i = 0; i < cursorCount; i++) {

                values.add(subCursor.getString(subCursor.getColumnIndex("category_name")));

                /*Toast.makeText(getActivity(), "Id:" + subCursor.getString(0) + "\n" +
                                "İsim:" + subCursor.getString(1),
                        Toast.LENGTH_SHORT).show();*/



               subCursor.moveToNext();
            }

        }

        //categoriesCursor.close();

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,values);

        ListView lv=(ListView)getActivity().findViewById(R.id.listviewKategori);
        lv.setAdapter(adapter);


        db.close();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView=inflater.inflate(R.layout.fragment_kategori, container, false);
        ((MainActivity2)getActivity()).getSupportActionBar().setTitle("Kategoriler");


        //noinspection deprecation

        Button btnGecis = mainView.findViewById(R.id.buttonKategoriEkle);
        btnGecis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Diğer fragmente geçiş yap
                Fragment yeniFragment = new CategoriesAdd();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, yeniFragment);
                transaction.addToBackStack(null); // Geri butonu ile geri dönülebilirlik ekler
                transaction.commit();
            }
        });







        return mainView;
    }




}
