package com.example.lab2.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.lab2.App;
import com.example.lab2.DB.DBHelper;
import com.example.lab2.Settings.FontSettings;
import com.example.lab2.Settings.LanguageSettings;
import com.example.lab2.Settings.AnotherSettings;
import com.example.lab2.R;

import java.util.Arrays;
import java.util.Locale;

public class SettingsActivity extends PreferenceActivity {

    SharedPreferences sp;
    int current_language;
    int current_font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String font = sp.getString(AnotherSettings.FONT_SETTINGS.getSetting(), FontSettings.MEDIUM.getFont()[0]);
        String listValue = sp.getString(AnotherSettings.LANGUAGE_SETTINGS.getSetting(), LanguageSettings.ENGLISH_RUS.getLanguage());
        Configuration configuration = new Configuration();

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        if (sp.getBoolean(AnotherSettings.THEME_SETTINGS.getSetting(), true)) {
            setTheme(R.style.Theme_AppCompat);
        }

        Locale locale;
        assert listValue != null;
        if (listValue.toLowerCase().equals(LanguageSettings.ENGLISH_ENG.getLanguage()) || listValue.toLowerCase().equals(LanguageSettings.ENGLISH_RUS.getLanguage())) {
            current_font = 1;
            locale = new Locale(LanguageSettings.LANGUAGE_ENG.getLanguage());
        } else {
            current_font = 0;
            locale = new Locale(LanguageSettings.LANGUAGE_RUS.getLanguage());
        }
        Locale.setDefault(locale);
        configuration.locale = locale;

        assert font != null;
        if (Arrays.asList(FontSettings.MEDIUM.getFont()).contains(font.toLowerCase())) {
            current_language = 0;
            configuration.fontScale = (float) 1;
        }
        else {
            current_language = 1;
            configuration.fontScale = (float) 1.2;
        }

        getBaseContext().getResources().updateConfiguration(configuration, null);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new ChangeSettingsFragment()).commit();
        super.onCreate(savedInstanceState);
    }

    public static class ChangeSettingsFragment extends PreferenceFragment {

        private DBHelper db;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            db = App.getInstance().getDatabase();
            addPreferencesFromResource(R.xml.settings_activity);
            ListPreference language = (ListPreference) findPreference(AnotherSettings.LANGUAGE_SETTINGS.getSetting());
            ListPreference font = (ListPreference) findPreference(AnotherSettings.FONT_SETTINGS.getSetting());
            Preference theme = findPreference(AnotherSettings.THEME_SETTINGS.getSetting());
            Preference button = findPreference(AnotherSettings.DELETE_FUNCTION.getSetting());
            font.setValueIndex(((SettingsActivity) getActivity()).current_language);
            language.setValueIndex(((SettingsActivity) getActivity()).current_font);
            language.setOnPreferenceChangeListener(this::onLanguageChange);
            font.setOnPreferenceChangeListener(this::onFontChange);
            theme.setOnPreferenceChangeListener(this::onThemeChange);
            button.setOnPreferenceClickListener(this::onDeleteClick);
        }

        private boolean onLanguageChange(Preference preference, Object newValue) {
            Locale locale;
            if (newValue.toString().toLowerCase().equals(LanguageSettings.ENGLISH_ENG.getLanguage()) || newValue.toString().toLowerCase().equals(LanguageSettings.ENGLISH_RUS.getLanguage())) {
                locale = new Locale(LanguageSettings.LANGUAGE_ENG.getLanguage());
            } else {
                locale = new Locale(LanguageSettings.LANGUAGE_RUS.getLanguage());
            }
            Locale.setDefault(locale);
            Configuration configuration = new Configuration();
            configuration.locale = locale;
            getActivity().getResources().updateConfiguration(configuration, null);
            getActivity().recreate();
            return true;
        }

        private boolean onFontChange(Preference preference, Object newValue) {
            Configuration configuration = getResources().getConfiguration();
            if (Arrays.asList(FontSettings.MEDIUM.getFont()).contains(newValue.toString().toLowerCase())) {
                configuration.fontScale = (float) 1;
            }
            else {
                configuration.fontScale = (float) 1.2;
            }
            DisplayMetrics metrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
            metrics.scaledDensity = configuration.fontScale * metrics.density;
            getActivity().getBaseContext().getResources().updateConfiguration(configuration, metrics);
            getActivity().recreate();
            return true;
        }

        private boolean onThemeChange(Preference preference, Object newValue) {
            if ((boolean) newValue) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            getActivity().recreate();
            return true;
        }

        private boolean onDeleteClick(Preference preference) {
            db.dbTimer().DeleteAll();
            Intent intent = new Intent();
            getActivity().setResult(RESULT_OK, intent);
            getActivity().finish();
            return true;
        }
    }
}
