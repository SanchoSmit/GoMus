package ua.onpu.project.gomus.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import ua.onpu.project.gomus.R;

public class SettingsActivity extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.settings_title);

        // Get preference fragment
        getFragmentManager().beginTransaction().replace(R.id.content_settings, new MyPreferenceFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
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
                    changeLanguage.setValue(getResources().getString(R.string.english));
                    break;
                case "ru":
                    changeLanguage.setValue(getResources().getString(R.string.ukrainian));
                    break;
            }

            // Set onChange listener
            changeLanguage.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {

                    if (getResources().getStringArray(R.array.application_languages)[0].equals((String)newValue)){
                        editor.putString("application_language", "en");
                        editor.apply();
                        showRestartApplicationAlertDialog();
                    } else if (getResources().getStringArray(R.array.application_languages)[1].equals((String)newValue)){
                        editor.putString("application_language", "ru");
                        editor.apply();
                        showRestartApplicationAlertDialog();
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

        /**
         * Method that shows dialog with text about application
         */
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

        /**
         * Method that shows dialog to restart application
         */
        private void showRestartApplicationAlertDialog(){

            AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getActivity());
            mDialogBuilder.setTitle(getResources().getString(R.string.restart_title));
            mDialogBuilder.setMessage(getResources().getString(R.string.restart_info));
            mDialogBuilder.setPositiveButton(getResources().getString(R.string.restart),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            restartApplication();
                        }
                    });
            mDialogBuilder.setNegativeButton(getResources().getString(R.string.later),
                    new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog alertDialog = mDialogBuilder.create();
            alertDialog.show();
        }

        /**
         * Method that restarts application
         */
        private void restartApplication(){

            Intent i = getActivity().getBaseContext()
                    .getPackageManager()
                    .getLaunchIntentForPackage(
                            getActivity().getBaseContext().getPackageName()
                    );

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }
}