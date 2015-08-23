package com.m2team.xna.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.os.Bundle;

import com.m2team.xna.R;
import com.m2team.xna.utils.Common;
import com.m2team.xna.utils.Constant;

import java.util.Locale;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class PreferenceSetting extends PreferenceActivity {
    ListPreference dataPref, pref_theme_color;
    Preference aboutPref;
    SharedPreferences.Editor edit;
    String vi_key, en_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
        dataPref = (ListPreference) findPreference("pref_language");
        pref_theme_color = (ListPreference) findPreference("pref_theme_color");
        aboutPref = findPreference("pref_about");

         aboutPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
             @Override
             public boolean onPreferenceClick(Preference preference) {
                 startActivity(new Intent(PreferenceSetting.this, AboutActivity.class));
                 return false;
             }
         });
        setLanguage();
        setThemeColor();
    }

    private void setLanguage() {
        vi_key = getString(R.string.vn_lang_key);
        en_key = getString(R.string.en_lang_key);
        final SharedPreferences sharedPreferences = getSharedPreferences(Constant.SHARE_PREF_FILE_NAME, Context.MODE_PRIVATE);
        String lang = sharedPreferences.getString(Constant.SHARE_PREF_KEY_LANGUAGE, null);
        if (lang == null) {
            lang = en_key;
            Common.putSharedPrefStringValue(this, Constant.SHARE_PREF_KEY_LANGUAGE, lang);
        }
        dataPref.setValue(lang);
        dataPref.setSummary(lang.equals(vi_key) ? R.string.vn_lang_value : R.string.en_lang_value);
        dataPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (newValue.equals(vi_key)) {
                    dataPref.setSummary(getString(R.string.vn_lang_value));
                } else if (newValue.equals(en_key)) {
                    dataPref.setSummary(getString(R.string.en_lang_value));
                }
                dataPref.setValue(newValue.toString());
                Common.putSharedPrefStringValue(PreferenceSetting.this, Constant.SHARE_PREF_KEY_LANGUAGE, newValue.toString());
                setLocale(newValue.toString());
                return false;
            }
        });
    }

    private void setThemeColor() {
        String color = Common.getSharedPrefStringValue(this, Constant.SHARE_PREF_KEY_THEME_COLOR);
        if (color == null) {
            color = getString(R.string.blue);
            Common.putSharedPrefStringValue(this, Constant.SHARE_PREF_KEY_THEME_COLOR, color);
        }
        pref_theme_color.setValue(color);
        pref_theme_color.setSummary(color);
        pref_theme_color.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                pref_theme_color.setSummary(newValue.toString());
                pref_theme_color.setValue(newValue.toString());
                Common.putSharedPrefStringValue(PreferenceSetting.this, Constant.SHARE_PREF_KEY_THEME_COLOR, newValue.toString());
                restartMainActivity();
                return false;
            }
        });
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        onConfigurationChanged(conf);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        restartMainActivity();
    }

    private void restartMainActivity() {
        Intent refresh = new Intent(this, MyActivity.class);
        refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(refresh);
        finish();
        refresh = new Intent(this, PreferenceSetting.class);
        refresh.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(refresh);
    }


}
