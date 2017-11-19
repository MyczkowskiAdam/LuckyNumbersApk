package com.luckynumbers.mycax.luckynumbers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

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
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().replace(
                    R.id.fragmentContainer, new LuckyNumbersFragment())
                    .commit();
        }
    }

}
