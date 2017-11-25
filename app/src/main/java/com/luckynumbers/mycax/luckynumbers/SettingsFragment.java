package com.luckynumbers.mycax.luckynumbers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.content.Intent;
import android.net.Uri;

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.pref_settings);

        Preference vReleases = (Preference) findPreference("build_app_version");
        vReleases.setOnPreferenceClickListener(this);

        Preference vGithub = (Preference) findPreference("app_github_link");
        vGithub.setOnPreferenceClickListener(this);

        Preference vDeleteAll = (Preference) findPreference("app_delete_results");
        vDeleteAll.setOnPreferenceClickListener(this);

    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference == findPreference("build_app_version")) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/MyczkowskiAdam/LuckyNumbersApk/releases")));
        }
        else if (preference == findPreference("app_github_link")) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/MyczkowskiAdam/LuckyNumbersApk")));
        }
        else if (preference == findPreference("app_delete_results")) {
            new AlertDialog.Builder(getActivity())
                    .setIcon(R.drawable.ic_warning_black_24dp)
                    .setTitle("Clear database")
                    .setMessage("Are you sure you want clear all contents of the database?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DataBHelper database = new DataBHelper(getActivity());
                            database.clearDatabase();
                            database.close();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        return true;
    }
}
