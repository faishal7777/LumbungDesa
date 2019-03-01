package com.runupstdio.lumbungdesa;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class NavigationBar extends AppCompatActivity
    implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar);

        BottomNavigationView navigation = findViewById(R.id.bottom_nav);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    private boolean loadFragment (Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.nav_beranda:
                setTheme(R.style.AppTheme);
                fragment = new BerandaFragment();
                break;

            case R.id.nav_temukan:
                setTheme(R.style.AppTheme);
                fragment = new TemukanFragment();
                break;

            case R.id.nav_akun:
                setTheme(R.style.AppTheme);
                fragment = new AkunFragment();
                break;
        }
        return loadFragment(fragment);
    }
}
