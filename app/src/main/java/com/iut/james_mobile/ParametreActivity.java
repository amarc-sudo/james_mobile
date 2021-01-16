package com.iut.james_mobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;


import com.iut.james_mobile.models.Professeur;

import java.util.Locale;

import yuku.ambilwarna.AmbilWarnaDialog;


public class ParametreActivity extends AppCompatActivity {
    private ConstraintLayout constraintLayout;
    private int defaultColor;
    private SharedPreferences sharedPreferences;
    // private Button BT_goSignature;  // inutile ici puisqu'on n'utilise pas ce bouton
    private Professeur professeur;
    private Spinner spinnerLanguages;
    //private int languagesComplete[] = {R.string.francais, R.string.anglais, R.string.espagnol};
    private String languagesAbr[] = {"fr", "en", "es"};
    private LanguageModifier languageModifier;
    private String language;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        languageModifier = new LanguageModifier();
        setContentView(R.layout.activity_parametre);
        constraintLayout = findViewById(R.id.parametreLayout);
        spinnerLanguages = findViewById(R.id.SP_languages);
        sharedPreferences = this.getSharedPreferences("com.iut.james_mobile", Context.MODE_PRIVATE);
        constraintLayout.setBackgroundColor(sharedPreferences.getInt("color", 0));
        Intent intent = getIntent();
        professeur = (Professeur) intent.getSerializableExtra("professeur");
        //BT_goSignature=findViewById(R.id.BT_goSignature);  // inutile ici puisqu'on n'utilise pas ce bouton
        this.setValuesSpinnerLanguages();
    }

    public void openColorPicker(View view) {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                defaultColor = color;
                constraintLayout.setBackgroundColor(defaultColor);
                sharedPreferences.edit().putInt("color", defaultColor).apply();
            }
        });
        colorPicker.show();
    }

    public void setValuesSpinnerLanguages() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguages.setAdapter(adapter);
        spinnerLanguages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                language=adapterView.getItemAtPosition(i).toString();
                language=language.substring(0,2).toLowerCase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void LanguageChangementButton(View view){
        sharedPreferences.edit().putString("language", language).apply();
        languageModifier.setLanguage(sharedPreferences.getString("language", languagesAbr[0]), ParametreActivity.this);
        finish();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void goSignature(View view) { //Il faut renommer la méthode
        Intent intent = new Intent(this, SignatureActivity.class);
        intent.putExtra("professeur", professeur);
        startActivity(intent);
    }

    public void goChangeMail(View view) { //Il faut renommer la méthode
        Intent intent = new Intent(this, EmailChangeActivity.class);
        intent.putExtra("professeur", professeur);
        startActivity(intent);
    }

    public void goChangePassword(View view) { //Il faut renommer la méthode
        Intent intent = new Intent(this, EmailChangeActivity.class);
        intent.putExtra("professeur", professeur);
        startActivity(intent);
    }
}