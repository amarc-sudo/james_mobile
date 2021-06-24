package com.iut.james_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iut.james_mobile.R;
import com.iut.james_mobile.models.Professeur;

import lombok.SneakyThrows;

public class WelcomeActivity extends AppCompatActivity {

    private Professeur professeur;

    private TextView TV_welcome;


    private Button BT_settings;  // inutile ici puisqu'on n'utilise pas ce bouton

    private SharedPreferences sharedPreferences;

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Intent intent = getIntent();
        professeur = (Professeur) intent.getSerializableExtra("professeur");
        if (professeur.isHasSigned() == false) {
            GoSignature();
        }
        TV_welcome = findViewById(R.id.TV_welcome);
        TV_welcome.setText(getResources().getString(R.string.bonjour) + " " + professeur.getPersonne().getNom().toUpperCase() + " " + professeur.getPersonne().getPrenom());
        BT_emargement = findViewById(R.id.BT_emargement);
        sharedPreferences = this.getSharedPreferences("com.iut.james_mobile", Context.MODE_PRIVATE);
    }

    @Override
    public void onResume(){
        super.onResume();
        LanguageModifier languageModifier = new LanguageModifier();
        languageModifier.setLanguage(sharedPreferences.getString("language", "fr"), this);
    }

    public void GoEmargement(View view) {
        Intent intent = new Intent(this, AppelActivity.class);
        intent.putExtra("professeur", professeur);
        startActivity(intent);
    }

    public void GoSignature() {
        Intent intent = new Intent(this, SignatureActivity.class);
        intent.putExtra("professeur", professeur);
        startActivity(intent);
    }

    public void goParametre(View view) {
        Intent intent = new Intent(this, ParametreActivity.class);
        intent.putExtra("professeur", professeur);
        startActivity(intent);
    }
}