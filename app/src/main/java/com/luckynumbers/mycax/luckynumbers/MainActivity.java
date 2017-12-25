package com.luckynumbers.mycax.luckynumbers;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity implements OnTabSelectListener {

    public BottomBar navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("app_dark_theme",false) ? R.style.AppTheme_Dark : R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = (BottomBar) findViewById(R.id.navigation);
        navigation.setOnTabSelectListener(this);
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

    public String getMainTag() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_main, null);
        return (String) view.getTag();
    }
}
