package com.iut.james_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.iut.james_mobile.models.Professeur;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {
    private Professeur professeur;
    private TextView nomProfesseur;
    private TextView emailProfesseur;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        professeur = (Professeur) intent.getSerializableExtra("professeur");
        nomProfesseur=findViewById(R.id.TV_nomProfesseur);
        emailProfesseur=findViewById(R.id.TV_emailProfesseur);
        sharedPreferences = this.getSharedPreferences("com.iut.james_mobile", Context.MODE_PRIVATE);
        this.setTextProfileProfesseur();
    }

    public void setTextProfileProfesseur(){
        nomProfesseur.setText(professeur.getPersonne().getNom().toUpperCase() + " " + professeur.getPersonne().getPrenom());
        emailProfesseur.setText(sharedPreferences.getString("login", "Votre email devrait s'afficher ici" ));
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

    public void goParametreFromProfile(View view) { //Il faut renommer la méthode
        //Intent intent = new Intent(this, PasswordChangeActivity.class);
        //intent.putExtra("professeur", professeur);
        //this.startActivity(intent);
        this.finish();
    }
}
