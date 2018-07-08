package com.rosalina.pemantauanlab;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.rosalina.pemantauanlab.Fragment.Account_frag;
import com.rosalina.pemantauanlab.Fragment.Data_frag;
import com.rosalina.pemantauanlab.Fragment.ListLaporan_frag;
import com.rosalina.pemantauanlab.Fragment.Home_frag;
import com.rosalina.pemantauanlab.Fragment.Lapor_frag;

public class Menuactivity extends AppCompatActivity {
    private ActionBar toolbar;
    private int getlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuactivity);
        //Toolbar
        toolbar = getSupportActionBar();


        getlogin = getIntent().getExtras().getInt("keylogin");
        System.out.println("getlogin :" +getlogin);

        //Navbar
        BottomNavigationView navigationView = findViewById(R.id.menulab);
        if (getlogin == 2){
            navigationView.getMenu().removeItem(R.id.data_menu);
            navigationView.getMenu().removeItem(R.id.history_menu);
        }

        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        // load the store fragment by default
        toolbar.setTitle("Home");
        loadFragment(new Home_frag());

    }

    private void loadFragment(Fragment fragment) {
        //loadfragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment)
                .commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.home_menu:
                        fragment = new Home_frag();
                        loadFragment(fragment);
                        toolbar.setTitle("Home");
                        return true;
                    case R.id.lapor_menu:
                        fragment = new Lapor_frag();
                        loadFragment(fragment);
                        toolbar.setTitle("Lapor");
                        return true;
                    case R.id.history_menu:
                        fragment = new ListLaporan_frag();
                        loadFragment(fragment);
                        toolbar.setTitle("List Laporan");
                        return true;
                    case R.id.data_menu:
                        fragment = new Data_frag();
                        loadFragment(fragment);
                        toolbar.setTitle("Data");
                        return true;
                    case R.id.account_menu:
                        fragment = new Account_frag();
                        loadFragment(fragment);
                        toolbar.setTitle("Akun");
                        return true;
            }
            return false;
        }
    };

}