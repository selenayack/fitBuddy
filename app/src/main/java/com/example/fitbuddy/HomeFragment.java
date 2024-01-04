package com.example.fitbuddy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        LinearLayout linearLayout1 = view.findViewById(R.id.linearlayout); // Bu kısmı kendi layoutunuzdaki LinearLayout'a uygun ID ile değiştirin

        // Tıklama olayını dinlemek için onClickListener ekleyin
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Yeni bir activity başlatmak için Intent kullanarak startActivity metodunu çağırın
                Intent intent = new Intent(getActivity(), user_infos.class); // YourNewActivity yerine açmak istediğiniz Activity'nin adını yazın
                startActivity(intent);
            }
        });;



        Context context = getContext(); // Context'i uygun şekilde alın
        String dbPath = context.getDatabasePath("fitBuddyDiet.db").getAbsolutePath();
        Log.d("DatabasePath", "Veritabanı Yolu: " + dbPath);

        // Database işlemleri burada gerçekleştirilebilir
        DbAdapter db = new DbAdapter(requireContext());
        db.open();

        int rows=db.count("food");

        if(rows<1){
            Toast.makeText(requireContext(), "Yükleniyor", Toast.LENGTH_SHORT).show();
            DBSetupInsert setupInsert=new DBSetupInsert(requireContext());
            setupInsert.insertAllFood();
            setupInsert.insertAllCategories();
            Toast.makeText(requireContext(), "Yüklendi", Toast.LENGTH_SHORT).show();



        }



        //db.close();









        return view;
    }
}
