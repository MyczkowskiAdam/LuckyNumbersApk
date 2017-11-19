package com.luckynumbers.mycax.luckynumbers;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.content.Intent;
import android.net.Uri;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.pref_settings);

        Preference vReleases = (Preference) findPreference("build_app_version");
        vReleases.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/MyczkowskiAdam/LuckyNumbersApk/releases")));
                return true;
            }
        });

        Preference vGithub = (Preference) findPreference("app_github_link");
        vGithub.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/MyczkowskiAdam/LuckyNumbersApk")));
                return true;
            }
        });
    }
}
