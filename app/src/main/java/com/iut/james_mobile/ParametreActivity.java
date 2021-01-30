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
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;


import com.iut.james_mobile.models.Professeur;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import yuku.ambilwarna.AmbilWarnaDialog;


public class ParametreActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private int defaultColor;
    private SharedPreferences sharedPreferences;
    // private Button BT_goSignature;  // inutile ici puisqu'on n'utilise pas ce bouton
    private Professeur professeur;
    private Spinner spinnerLanguages;
    private String languagesAbr[] = {"fr", "en", "es", "ro"};
    private LanguageModifier languageModifier;
    private String language;
    private int positionLanguage;
    private List listLanguages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        languageModifier = new LanguageModifier();
        this.setContentView(R.layout.activity_parametre);
        linearLayout = findViewById(R.id.parametreLayout);
        spinnerLanguages = findViewById(R.id.SP_languages);
        sharedPreferences = this.getSharedPreferences("com.iut.james_mobile", Context.MODE_PRIVATE);
        Intent intent = getIntent();
        professeur = (Professeur) intent.getSerializableExtra("professeur");
        listLanguages= new ArrayList<String>();
        this.addLanguagesToArrayList();
        //BT_goSignature=findViewById(R.id.BT_goSignature);  // inutile ici puisqu'on n'utilise pas ce bouton
        this.setValuesSpinnerLanguages();
    }

    public void setValuesSpinnerLanguages() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguages.setAdapter(adapter);
        spinnerLanguages.setSelection(sharedPreferences.getInt("positionLanguage",0), true);
        spinnerLanguages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                language=adapterView.getItemAtPosition(i).toString();
                language=language.substring(0,2).toLowerCase();
                positionLanguage=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void LanguageChangementButton(View view){
        sharedPreferences.edit().putString("language", language).apply();
        languageModifier.setLanguage(sharedPreferences.getString("language", languagesAbr[0]), ParametreActivity.this);
        sharedPreferences.edit().putInt("positionLanguage", positionLanguage).apply();
        this.finish();
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.putExtra("professeur", professeur);
        this.startActivity(intent);
    }

    public void goSignature(View view) { //Il faut renommer la méthode
        Intent intent = new Intent(this, SignatureActivity.class);
        intent.putExtra("professeur", professeur);
        this.startActivity(intent);
    }

    public void goChangeMail(View view) { //Il faut renommer la méthode
        Intent intent = new Intent(this, EmailChangeActivity.class);
        intent.putExtra("professeur", professeur);
        this.startActivity(intent);
    }

    public void goChangePassword(View view) { //Il faut renommer la méthode
        Intent intent = new Intent(this, PasswordChangeActivity.class);
        intent.putExtra("professeur", professeur);
        this.startActivity(intent);
    }

    public ArrayList<String> addLanguagesToArrayList(){
        listLanguages.add(getResources().getString(R.string.francais));
        listLanguages.add(getResources().getString(R.string.anglais));
        listLanguages.add(getResources().getString(R.string.espagnol));
        return (ArrayList<String>) listLanguages;
    }
}