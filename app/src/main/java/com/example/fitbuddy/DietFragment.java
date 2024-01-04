package com.example.fitbuddy;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;



public class DietFragment extends Fragment {





        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_diet, container, false);

            LinearLayout linearLayout1 = view.findViewById(R.id.linearlayout1); // Bu kısmı kendi layoutunuzdaki LinearLayout'a uygun ID ile değiştirin

            // Tıklama olayını dinlemek için onClickListener ekleyin
            linearLayout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Yeni bir activity başlatmak için Intent kullanarak startActivity metodunu çağırın
                    Intent intent = new Intent(getActivity(), Diet1.class); // YourNewActivity yerine açmak istediğiniz Activity'nin adını yazın
                    startActivity(intent);
                }
            });


    LinearLayout linearLayout2 = view.findViewById(R.id.linearlayout2); // Bu kısmı kendi layoutunuzdaki LinearLayout'a uygun ID ile değiştirin

    // Tıklama olayını dinlemek için onClickListener ekleyin
            linearLayout2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Yeni bir activity başlatmak için Intent kullanarak startActivity metodunu çağırın
            Intent intent = new Intent(getActivity(), Diet2.class); // YourNewActivity yerine açmak istediğiniz Activity'nin adını yazın
            startActivity(intent);
        }
    });
            LinearLayout linearLayout3 = view.findViewById(R.id.linearlayout3); // Bu kısmı kendi layoutunuzdaki LinearLayout'a uygun ID ile değiştirin

            // Tıklama olayını dinlemek için onClickListener ekleyin
            linearLayout3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Yeni bir activity başlatmak için Intent kullanarak startActivity metodunu çağırın
                    Intent intent = new Intent(getActivity(), Diet3.class); // YourNewActivity yerine açmak istediğiniz Activity'nin adını yazın
                    startActivity(intent);
                }
            });
            LinearLayout linearLayout4 = view.findViewById(R.id.linearlayout4); // Bu kısmı kendi layoutunuzdaki LinearLayout'a uygun ID ile değiştirin

            // Tıklama olayını dinlemek için onClickListener ekleyin
            linearLayout4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Yeni bir activity başlatmak için Intent kullanarak startActivity metodunu çağırın
                    Intent intent = new Intent(getActivity(), Diet4.class); // YourNewActivity yerine açmak istediğiniz Activity'nin adını yazın
                    startActivity(intent);
                }
            });

            LinearLayout linearLayout5 = view.findViewById(R.id.linearlayout5); // Bu kısmı kendi layoutunuzdaki LinearLayout'a uygun ID ile değiştirin

            // Tıklama olayını dinlemek için onClickListener ekleyin
            linearLayout5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Yeni bir activity başlatmak için Intent kullanarak startActivity metodunu çağırın
                    Intent intent = new Intent(getActivity(), Diet5.class); // YourNewActivity yerine açmak istediğiniz Activity'nin adını yazın
                    startActivity(intent);
                }
            });


            return view;
}
}


