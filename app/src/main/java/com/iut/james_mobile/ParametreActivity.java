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
    private SharedPreferences sharedPreferences;
    private Professeur professeur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_parametre);
        linearLayout = findViewById(R.id.parametreLayout);
        sharedPreferences = this.getSharedPreferences("com.iut.james_mobile", Context.MODE_PRIVATE);
        Intent intent = getIntent();
        professeur = (Professeur) intent.getSerializableExtra("professeur");
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
        this.finish();
        Intent intent = new Intent(this, PreferenceActivity.class);
        intent.putExtra("professeur", professeur);
        this.startActivity(intent);
    }



}