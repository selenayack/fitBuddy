package com.example.fitbuddy;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity2 extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private HomeFragment homeFragment = new HomeFragment();
    private AccountFragment accountFragment = new AccountFragment();
    private  DietFragment  dietFragment = new  DietFragment();
    private  FitnessFragment  fitnessFragment = new  FitnessFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        homeFragment = new HomeFragment();
        accountFragment = new AccountFragment();
        dietFragment = new DietFragment();
        fitnessFragment = new FitnessFragment();
        setFragment(homeFragment);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home_bottom) {
                    setFragment(homeFragment);
                    return true;
                } else if (itemId == R.id.diet_bottom) {
                    setFragment(dietFragment);
                    return true;
                } else if (itemId == R.id.fitness_bottom) {
                    setFragment(fitnessFragment);
                    return true;
                } else if (itemId == R.id.account_bottom) {
                    setFragment(accountFragment);
                    return true;
                }
                return false;
            }

        });

    }

    private void setFragment(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.commit();



    }



}
