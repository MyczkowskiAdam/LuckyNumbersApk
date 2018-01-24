package com.luckynumbers.mycax.luckynumbers;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import shortbread.Shortbread;
import shortbread.Shortcut;

public class MainActivity extends AppCompatActivity implements OnTabSelectListener{

    private BottomBar navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Shortbread.create(this);
        setTheme(PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("app_dark_theme",false) ? R.style.AppTheme_Dark : R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = findViewById(R.id.navigation);
        navigation.setOnTabSelectListener(this);
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        if (tabId == R.id.navigation_luckynumbers) launchFragment(new LuckyNumbersFragment());
        if (tabId == R.id.navigation_results) launchFragment(new ResultsFragment());
        if (tabId == R.id.navigation_settings) launchFragment(new SettingsFragment());
    }

    public String getMainTag() {
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.activity_main, null);
        return (String) view.getTag();
    }

    private void launchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    @SuppressWarnings("unused")
    @Shortcut(id = "luckynumbers", icon = R.drawable.ic_shortcut_clover, shortLabel = "Lucky Number")
    public void showLucky() {
        launchFragment(new LuckyNumbersFragment());
        navigation.selectTabWithId(R.id.navigation_luckynumbers);

    }

    @SuppressWarnings("unused")
    @Shortcut(id = "results", icon = R.drawable.ic_shortcut_view_headline, shortLabel = "Results")
    public void showResults() {
        launchFragment(new ResultsFragment());
        navigation.selectTabWithId(R.id.navigation_results);
    }

    @SuppressWarnings("unused")
    @Shortcut(id = "settings", icon = R.drawable.ic_shortcut_settings, shortLabel = "Settings")
    public void showSettings() {
        launchFragment(new SettingsFragment());
        navigation.selectTabWithId(R.id.navigation_settings);
    }
}
