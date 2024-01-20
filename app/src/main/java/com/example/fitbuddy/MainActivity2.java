package com.example.fitbuddy;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    private BottomNavigationView bottomNavigationView;
    private HomeFragment homeFragment = new HomeFragment();
    private AccountFragment accountFragment = new AccountFragment();
    private  DietFragment  dietFragment = new  DietFragment();
    private  FitnessFragment  fitnessFragment = new  FitnessFragment();
    private  ProfilFragment profilFragment = new ProfilFragment();
    private  HedefFragment hedefFragment = new HedefFragment();
    private  KategoriFragment kategoriFragment = new KategoriFragment();
    private YemekFragment yemekFragment = new YemekFragment();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);




        toolbar.setNavigationIcon(R.drawable.ic_custom_icon);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);
                navigationView = findViewById(R.id.nav_view);
                setupDrawerContent(navigationView);


            }
        });





        //bottomnavigation

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


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        selectDrawerItem(item);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        int itemId = menuItem.getItemId();

     if (itemId == R.id.hedef) {
            fragment = hedefFragment;
        } else if (itemId == R.id.kategori) {
            fragment = kategoriFragment;
        } else if (itemId == R.id.yemek) {
            fragment = yemekFragment;
        }

        if (fragment != null) {
            setFragment(fragment);
        }

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }




    private void setFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param item The selected item
     * @return true to display the item as the selected item
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
