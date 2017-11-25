package com.luckynumbers.mycax.luckynumbers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    public BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().replace(
                    R.id.fragmentContainer, new LuckyNumbersFragment())
                    .commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_luckynumbers:
                getFragmentManager().beginTransaction().replace(
                        R.id.fragmentContainer, new LuckyNumbersFragment())
                        .commit();
                return true;
            case R.id.navigation_results:
                getFragmentManager().beginTransaction().replace(
                        R.id.fragmentContainer, new ResultsFragment())
                        .commit();
                return true;
            case R.id.navigation_settings:
                getFragmentManager().beginTransaction().replace(
                        R.id.fragmentContainer, new SettingsFragment())
                        .commit();
                return true;
        }
        return false;
    }

    public void setNavigationVisibility(boolean visible) {
        if (navigation.isShown() && !visible) {
            navigation.setVisibility(View.GONE);
        }
        else if (!navigation.isShown() && visible){
            navigation.setVisibility(View.VISIBLE);
        }
    }
}
