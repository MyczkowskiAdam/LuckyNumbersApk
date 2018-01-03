package com.luckynumbers.mycax.luckynumbers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.design.widget.Snackbar;

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener, SwitchPreference.OnPreferenceChangeListener {

    private static final String BUILD_APP_VERSION = "build_app_version";
    private static final String APP_GITHUB_LINK = "app_github_link";
    private static final String APP_DELETE_RESULTS = "app_delete_results";
    private static final String APP_OPENSOURCE_LIBS = "app_opensource_libs";
    private static final String APP_DARK_THEME = "app_dark_theme";
    private static final String APP_ENABLE_GRID = "app_enable_grid";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.pref_settings);

        Preference vReleases = findPreference(BUILD_APP_VERSION);
        vReleases.setOnPreferenceClickListener(this);

        Preference vGithub = findPreference(APP_GITHUB_LINK);
        vGithub.setOnPreferenceClickListener(this);

        Preference vDeleteAll = findPreference(APP_DELETE_RESULTS);
        vDeleteAll.setOnPreferenceClickListener(this);

        Preference vAbout = findPreference(APP_OPENSOURCE_LIBS);
        vAbout.setOnPreferenceClickListener(this);

        SwitchPreference sDarkTheme = (SwitchPreference) findPreference(APP_DARK_THEME);
        sDarkTheme.setOnPreferenceChangeListener(this);

        SwitchPreference sGridView = (SwitchPreference) findPreference(APP_ENABLE_GRID);
        if (((MainActivity) getActivity()).getMainTag().equals("phone")) {
            getPreferenceScreen().removePreference(sGridView);
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean onPreferenceChange(Preference preference, Object object) {
        getActivity().finish();
        final Intent intent = getActivity().getIntent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getActivity().startActivity(intent);
        return true;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference == findPreference(BUILD_APP_VERSION)) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/MyczkowskiAdam/LuckyNumbersApk/releases")));
        } else if (preference == findPreference(APP_GITHUB_LINK)) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/MyczkowskiAdam/LuckyNumbersApk")));
        } else if (preference == findPreference(APP_DELETE_RESULTS)) {
            new AlertDialog.Builder(getActivity())
                    .setIcon(!PreferenceManager.getDefaultSharedPreferences(getActivity())
                            .getBoolean(APP_DARK_THEME, false) ? R.drawable.ic_warning_black_24dp : R.drawable.ic_warning_white_24dp)
                    .setTitle("Clear database")
                    .setMessage("Are you sure you want clear all contents of the database?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @SuppressWarnings("ConstantConditions")
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DataBHelper database = new DataBHelper(getActivity());
                            database.clearDatabase();
                            database.close();
                            Snackbar.make(getView(), "Database successfully cleared!", Snackbar.LENGTH_SHORT).show();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        } else if (preference == findPreference(APP_OPENSOURCE_LIBS)) {
            new LibsBuilder()
                    .withActivityStyle(PreferenceManager.getDefaultSharedPreferences(getActivity())
                            .getBoolean(APP_DARK_THEME, false) ? Libs.ActivityStyle.DARK : Libs.ActivityStyle.LIGHT)
                    .withAutoDetect(true)
                    .withAboutIconShown(true)
                    .withAboutVersionShown(true)
                    .start(getActivity());
        }
        return true;
    }
}
