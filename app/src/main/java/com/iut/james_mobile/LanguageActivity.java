package com.iut.james_mobile;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class LanguageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_language);
        this.setAppLocale("en");
    }

    public void setAppLocale(String localCode){
        //Resources res= getApplicationContext().getResources();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        Configuration conf=getResources().getConfiguration();
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.JELLY_BEAN_MR1){
            conf.setLocale(new Locale(localCode.toLowerCase()));
        }
        else{
            conf.locale=new Locale(localCode.toLowerCase());
        }
        getResources().updateConfiguration(conf,dm);
    }
}
