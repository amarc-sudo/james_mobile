package com.iut.james_mobile.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.iut.james_mobile.R;
import com.iut.james_mobile.models.Professeur;


public class ParametreActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Professeur professeur;
    private Spinner spinnerLanguages;
    private String languagesAbr[] = {"fr", "en", "es", "ro"};
    private LanguageModifier languageModifier;
    private String language;
    private int positionLanguage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        languageModifier = new LanguageModifier();
        this.setContentView(R.layout.activity_parametre);
        spinnerLanguages = findViewById(R.id.SP_languages);
        sharedPreferences = this.getSharedPreferences("com.iut.james_mobile", Context.MODE_PRIVATE);
        Intent intent = getIntent();
        professeur = (Professeur) intent.getSerializableExtra("professeur");
        this.setValuesSpinnerLanguages();
    }

    public void setValuesSpinnerLanguages() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguages.setAdapter(adapter);
        spinnerLanguages.setSelection(sharedPreferences.getInt("positionLanguage", 0), true);
        positionLanguage = sharedPreferences.getInt("positionLanguage", 0);
        language = sharedPreferences.getString("language", languagesAbr[0]);
        spinnerLanguages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                language = adapterView.getItemAtPosition(i).toString();
                language = language.substring(0, 2).toLowerCase();
                positionLanguage = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void LanguageChangementButton(View view) {
        sharedPreferences.edit().putString("language", language).apply();
        languageModifier.setLanguage(sharedPreferences.getString("language", languagesAbr[0]), ParametreActivity.this);
        sharedPreferences.edit().putInt("positionLanguage", positionLanguage).apply();
        this.finish();
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.putExtra("professeur", professeur);
        this.startActivity(intent);
    }

    public void goSignature(View view) { //Il faut renommer la méthode
        int modificationSignature = 1;
        Intent intent = new Intent(this, SignatureActivity.class);
        intent.putExtra("professeur", professeur);
        intent.putExtra("modificationSignature", modificationSignature);
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

}