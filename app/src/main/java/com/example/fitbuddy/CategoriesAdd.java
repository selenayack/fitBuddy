package com.example.fitbuddy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class CategoriesAdd  extends Fragment {

    private View mainView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.fragment_categories_add, container, false);

        return mainView;


    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // onViewCreated içinde createNewKategori metodunu çağır
        createNewKategori();

        Button btnKaydet = mainView.findViewById(R.id.buttonKaydet);
        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createNewKategoriSave();
                Fragment yeniFragment = new KategoriFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, yeniFragment);
                transaction.addToBackStack(null); // Geri butonu ile geri dönülebilirlik ekler
                transaction.commit();
            }
        });
    }

    private void createNewKategoriSave() {
        DbAdapter db = new DbAdapter(getActivity()) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };
        db.open();

        int error = 0;
        EditText editTextİsim = getActivity().findViewById(R.id.edittextİsim);
        String stringİsim = editTextİsim.getText().toString();
        if (stringİsim.equals("")) {
            Toast.makeText(getActivity(), "Lütfen kategori ismi giriniz", Toast.LENGTH_SHORT).show();
            error = -1;
        }


        Spinner spinner = getActivity().findViewById(R.id.spinnerParent);
        String stringSpinnerKategoriParent = spinner.getSelectedItem().toString();

        String parentId;

        if (stringSpinnerKategoriParent.equals("-")) {
            parentId = "0";
        } else {

            parentId=null;



            String stringSpinnerKategoriParentSQL = db.quoteSmart(stringSpinnerKategoriParent);
            System.out.println("stringspinnerkategoriparentsql" + stringSpinnerKategoriParentSQL);
            String fields[] = new String[]{
                    "_id",
                    "category_name",
                    "category_parent_id"
            };

            Cursor findParentId = db.select("categories", fields, "category_name", stringSpinnerKategoriParent);
            System.out.println("findparent" + findParentId);

            if (findParentId != null) {
                try {
                    int rowCount = findParentId.getCount();
                    Log.d("DEBUG_TAG", "Satır sayısı: " + rowCount);

                    if (rowCount > 0 && findParentId.moveToFirst()) {
                        // Satır üzerinde işlemleri gerçekleştir
                        int categoryIndex = findParentId.getColumnIndex("category_parent_id");

                        if (categoryIndex != -1) {
                            parentId=findParentId.getString(0).toString();
                            //parentId = findParentId.getString(categoryIndex);
                            Log.d("DEBUG_TAG", "ParentId: " + parentId);
                        } else {
                            Log.e("ERROR_TAG", "category_parent_id sütunu bulunamadı");
                        }
                    } else {
                        Log.d("DEBUG_TAG", "Eşleşen satır bulunamadı");
                    }
                } finally {
                    // Finally bloğunda Cursor'ı kapat
                    findParentId.close();
                }
            } else {
                Log.e("ERROR_TAG", "Cursor null durumunda");
            }
        }


        if (error == 0) {
            if (parentId != null) {
                // parentId mevcut, işlemleri gerçekleştir
                String stringİsimSQL = db.quoteSmart(stringİsim);
                String parentIdSQL = db.quoteSmart(parentId);

                String input = "NULL, " + stringİsimSQL + ", " + parentIdSQL;
                db.insert("categories", "_id,category_name,category_parent_id", input);
                Log.e("ERROR_TAG", "İşlem tamam");
                Toast.makeText(getActivity(),"Yeni kategori oluşturuldu",Toast.LENGTH_LONG).show();

            } else {
                // parentId null ise, bir hata durumu olabilir, gerekli işlemleri yapın
                Log.e("ERROR_TAG", "parentId null, işlem gerçekleştirilemedi");
            }

        } else {
            // Hata durumu varsa gerekli işlemleri yapın
            Log.e("ERROR_TAG", "Hata durumu: " + error);
        }

        db.close();

    }










    private void createNewKategori() {

        DbAdapter db=new DbAdapter(getActivity()) {
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

