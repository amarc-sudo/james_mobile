package com.iut.james_mobile.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;


import com.iut.james_mobile.R;
import com.iut.james_mobile.activities.PreferenceActivity;
import com.iut.james_mobile.activities.ProfileActivity;
import com.iut.james_mobile.models.Professeur;


public class ParametreActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    // private Button BT_goSignature;  // inutile ici puisqu'on n'utilise pas ce bouton
    private Professeur professeur;
    private Spinner spinnerLanguages;
    private String languagesAbr[] = {"fr", "en", "es", "ro"};
    private LanguageModifier languageModifier;
    private String language;
    private int positionLanguage;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        languageModifier = new LanguageModifier();
        this.setContentView(R.layout.activity_parametre);
        linearLayout = findViewById(R.id.parametreLayout);
        sharedPreferences = this.getSharedPreferences("com.iut.james_mobile", Context.MODE_PRIVATE);
        Intent intent = getIntent();
        professeur = (Professeur) intent.getSerializableExtra("professeur");
    }

    @Override
    public void onResume(){
        super.onResume();
        LanguageModifier languageModifier = new LanguageModifier();
        languageModifier.setLanguage(sharedPreferences.getString("language", "fr"), this);
    }

    public void returnWelcomeActivity(View view){
        this.finish();
    }

    public void goProfProfile(View view){
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("professeur", professeur);
        this.startActivity(intent);
    }

    public void  goPreferencies(View view){
        Intent intent = new Intent(this, PreferenceActivity.class);
        intent.putExtra("professeur", professeur);
        this.startActivity(intent);
    }

}