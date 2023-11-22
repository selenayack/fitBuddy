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
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;



public class FitnessFragment extends Fragment {

    Button button1,button2;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fitness, container, false);


       Toolbar toolbar=view.findViewById(R.id.toolBar);


        button1=view.findViewById(R.id.startFitness);
        button2=view.findViewById(R.id.startFitness2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(),SporActivity1.class);
                startActivity(intent);

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(),SporActivity2.class);
                startActivity(intent);

            }
        });

        







        // Inflate the layout for this fragment
        return view;
    }



    public void afterage18(View view) {
        Intent intent=new Intent(getContext(),SporActivity2.class);
        startActivity(intent);
    }


    public void beforeage18(View view) {
        Intent intent=new Intent(getContext(),SporActivity1.class);
        startActivity(intent);

    }
}


