package com.iut.james_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.iut.james_mobile.apiobject.Professeur;

public class WelcomeActivity extends AppCompatActivity {

    private Professeur professeur;

    private TextView TV_welcome;

    private Button BT_emargement;  // inutile ici puisqu'on n'utilise pas ce bouton

    private Button BT_settings;  // inutile ici puisqu'on n'utilise pas ce bouton

    private LanguageActivity languageActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //languageActivity.setAppLocal("fr");
        Intent intent = getIntent();
        professeur = (Professeur) intent.getSerializableExtra("professeur");
        if (professeur.isHasSigned() == false) {
            GoSignature();
        }
        TV_welcome = findViewById(R.id.TV_welcome);
        TV_welcome.setText("Bonjour " + professeur.getPersonne().getNom().toUpperCase() + " " + professeur.getPersonne().getPrenom());
        BT_emargement = findViewById(R.id.BT_emargement);
    }

    public void GoEmargement(View view) {
        Intent intent = new Intent(this, AppelActivity.class);
        intent.putExtra("professeur", professeur);
        startActivity(intent);
    }

    public void GoSignature() {
        Intent intent = new Intent(this, SignatureActivity.class);
        intent.putExtra("professeur", professeur);
        this.finish();
        startActivity(intent);
    }

    public void goParametre(View view) {
        Intent intent = new Intent(this, ParametreActivity.class);
        intent.putExtra("professeur", professeur);
        startActivity(intent);
    }
}