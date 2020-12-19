package com.iut.james_mobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import yuku.ambilwarna.AmbilWarnaDialog;


public class ParametreActivity extends AppCompatActivity {
    private ConstraintLayout constraintLayout;
    private int defaultColor;
    private SharedPreferences sharedPreferences;




    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametre);
        constraintLayout=findViewById(R.id.parametreLayout);
        sharedPreferences = this.getSharedPreferences("com.iut.james_mobile", Context.MODE_PRIVATE);
        constraintLayout.setBackgroundColor(sharedPreferences.getInt("color", 0));
    }

    public void openColorPicker(View view){
        AmbilWarnaDialog colorPicker= new AmbilWarnaDialog(this, defaultColor,  new AmbilWarnaDialog.OnAmbilWarnaListener() {

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                    defaultColor= color;
                    constraintLayout.setBackgroundColor(defaultColor);
                    sharedPreferences.edit().putInt("color", defaultColor).apply();
            }
        }) ;
        colorPicker.show();
    }
}
