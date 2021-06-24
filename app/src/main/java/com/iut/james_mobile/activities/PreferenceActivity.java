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
import com.iut.james_mobile.activities.WelcomeActivity;
import com.iut.james_mobile.models.Professeur;

public class PreferenceActivity extends AppCompatActivity {

    private Spinner spinnerLanguages,spinnerDureeCours;
    private String languagesAbr[] = {"fr", "en", "es", "ro"};
    private LanguageModifier languageModifier;
    private String language;
    private int dureeCours;
    private int positionLanguage,positionDureeCours;
    private SharedPreferences sharedPreferences;
    private Professeur professeur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_preferencies);
        languageModifier = new LanguageModifier();
        spinnerLanguages = findViewById(R.id.SP_languages);
        spinnerDureeCours = findViewById(R.id.SP_dureeCoursSimple);
        sharedPreferences = this.getSharedPreferences("com.iut.james_mobile", Context.MODE_PRIVATE);
        Intent intent = getIntent();
        professeur = (Professeur) intent.getSerializableExtra("professeur");
        this.setValuesSpinnerLanguages();
        this.setValuesSpinnerCourseDuration();
    }

    @Override
    public void onResume(){
        super.onResume();
        LanguageModifier languageModifier = new LanguageModifier();
        languageModifier.setLanguage(sharedPreferences.getString("language", "fr"), this);
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

    public void setValuesSpinnerCourseDuration() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dureeCours, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDureeCours.setAdapter(adapter);
        spinnerDureeCours.setSelection(sharedPreferences.getInt("positionDureeCours", 0), true);
        positionDureeCours = sharedPreferences.getInt("positionDureeCours", 0);
        dureeCours = sharedPreferences.getInt("dureeCours", 1);
        spinnerDureeCours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dureeCours =  Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                positionDureeCours = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void changePreferencies(View view) {
        sharedPreferences.edit().putString("language", language).apply();
        sharedPreferences.edit().putInt("dureeCours", dureeCours).apply();
        languageModifier.setLanguage(sharedPreferences.getString("language", languagesAbr[0]),PreferenceActivity.this);
        sharedPreferences.edit().putInt("positionLanguage", positionLanguage).apply();
        sharedPreferences.edit().putInt("positionDureeCours", positionDureeCours).apply();
        this.finish();
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.putExtra("professeur", professeur);
        this.startActivity(intent);
    }

}
