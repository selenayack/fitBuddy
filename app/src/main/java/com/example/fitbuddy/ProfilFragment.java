package com.example.fitbuddy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ProfilFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity2)getActivity()).getSupportActionBar().setTitle("Profil");
        return inflater.inflate(R.layout.fragment_profil, container, false);
    }


}