package com.iut.james_mobile;

import android.content.Intent;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class LanguageModifier {
    public void setLanguage(String language, AppCompatActivity appCompatActivity) {
        String languageToLoad = language; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        appCompatActivity.getBaseContext().getResources().updateConfiguration(config, appCompatActivity.getBaseContext().getResources().getDisplayMetrics());
    }
}
