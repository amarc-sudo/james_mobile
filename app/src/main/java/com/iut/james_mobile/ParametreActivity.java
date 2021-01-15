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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.iut.james_mobile.apiobject.Professeur;

import java.util.Locale;

import yuku.ambilwarna.AmbilWarnaDialog;


public class ParametreActivity extends AppCompatActivity {
    private ConstraintLayout constraintLayout;
    private int defaultColor;
    private SharedPreferences sharedPreferences;
   // private Button BT_goSignature;  // inutile ici puisqu'on n'utilise pas ce bouton
    private Professeur professeur;
    private Spinner spinnerLanguages;
    private int languagesComplete []={R.string.francais, R.string.anglais,R.string.espagnol};
    private String languagesAbr []={"fr", "en","es"};


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        sharedPreferences = this.getSharedPreferences("com.iut.james_mobile", Context.MODE_PRIVATE);
        System.out.println("La langue actuelle est : " + sharedPreferences.getString("language", languagesAbr[1]));
        checkLanguage(sharedPreferences.getString("language",languagesAbr[1]));
        setContentView(R.layout.activity_parametre);
        constraintLayout=findViewById(R.id.parametreLayout);
        spinnerLanguages=findViewById(R.id.SP_languages);
        constraintLayout.setBackgroundColor(sharedPreferences.getInt("color", 0));
        Intent intent = getIntent();
        professeur = (Professeur) intent.getSerializableExtra("professeur");
        //BT_goSignature=findViewById(R.id.BT_goSignature);  // inutile ici puisqu'on n'utilise pas ce bouton
        this.setValuesSpinnerLanguages();
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

    public void setValuesSpinnerLanguages(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguages.setAdapter(adapter);
        spinnerLanguages.setSelection(0,false);
        spinnerLanguages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    sharedPreferences.edit().putString("language", adapterView.getItemAtPosition(i).toString()).apply();
                    String languageComplete=adapterView.getItemAtPosition(i).toString(); //Nom complet du language "Espagnol" par exemple
                    checkLanguage(languageComplete);

            }
                //System.out.println(sharedPreferences.getString("language", "en"));
             /*   String myString = getString(R.string.espagnol); //the value you want the position for
                ArrayAdapter myAdap = (ArrayAdapter) spinnerLanguages.getAdapter(); //cast to an ArrayAdapter
                int spinnerPosition = myAdap.getPosition(myString);
                spinnerLanguages.setSelection(spinnerPosition);
             */

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void checkLanguage(String languageStringComplete){
        for(int i=0;i<languagesAbr.length;i++) {
            if (languageStringComplete.equals(getBaseContext().getResources().getString(this.languagesComplete[i]))) {
                this.setLanguage(languagesAbr[i]);
                break;
            }
        }

    }

   public void setLanguage(String language) {
        String languageToLoad = language; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
       //recreate();
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