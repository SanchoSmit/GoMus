package ua.onpu.project.gomus.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;

import ua.onpu.project.gomus.R;

public class SettingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        //TODO Fix ActionBar error

        // Get preference fragment
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    public static class MyPreferenceFragment extends PreferenceFragment {

        private ListPreference changeLanguage;
        private Preference about;

        private SharedPreferences preferences;
        private SharedPreferences.Editor editor;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            // Find preferences
            changeLanguage = (ListPreference) findPreference("language_change");
            about = findPreference("about");

            // Initialize SharedPreferences & editor
            preferences = getActivity().getSharedPreferences("GoMusSetting", MODE_PRIVATE);
            editor = preferences.edit();

            // Set default list value
            switch (preferences.getString("application_language", "en")){
                case "en":
                    changeLanguage.setValue("English");
                    break;
                case "ru":
                    changeLanguage.setValue("Ukrainian");
                    break;
            }

            // Set onChange listener
            changeLanguage.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {

                    switch ((String)newValue){
                        case "English":
                            editor.putString("application_language", "en");
                            editor.apply();
                            break;
                        case "Ukrainian":
                            editor.putString("application_language", "ru");
                            editor.apply();
                            break;
                    }
                    return true;
                }
            });

            // About click listener
            about.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    showApplicationAboutAlertDialog();
                    return true;
                }
            });
        }

        // Alert dialog with text about
        private void showApplicationAboutAlertDialog(){

            LayoutInflater li = getActivity().getLayoutInflater();
            View promptsView = li.inflate(R.layout.dialog_about, null);
            AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getActivity());
            mDialogBuilder.setView(promptsView);
            mDialogBuilder.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = mDialogBuilder.create();
            alertDialog.show();
        }
    }
}