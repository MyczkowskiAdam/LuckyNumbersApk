package com.luckynumbers.mycax.luckynumbers;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity implements OnTabSelectListener {

    public BottomBar navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("app_dark_theme",false)) {
            setTheme(R.style.AppTheme_Dark);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = (BottomBar) findViewById(R.id.navigation);
        navigation.setOnTabSelectListener(this);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().replace(
                    R.id.fragmentContainer, new LuckyNumbersFragment())
                    .commit();
        }
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        if (tabId == R.id.navigation_luckynumbers) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                    .replace(R.id.fragmentContainer, new LuckyNumbersFragment())
                    .commit();
        }
        if (tabId == R.id.navigation_results) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                    .replace(R.id.fragmentContainer, new ResultsFragment())
                    .commit();
        }
        if (tabId == R.id.navigation_settings) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                    .replace(R.id.fragmentContainer, new SettingsFragment())
                    .commit();
        }
    }
}
