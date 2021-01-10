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

    private Button BT_signature_prof;

    private Button BT_emargement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Intent intent=getIntent();
        professeur= (Professeur) intent.getSerializableExtra("professeur");
        TV_welcome=findViewById(R.id.TV_welcome);
        TV_welcome.setText("Bonjour "+professeur.getPersonne().getNom().toUpperCase()+" "+professeur.getPersonne().getPrenom());
        BT_signature_prof=findViewById(R.id.BT_signature_prof);
        BT_emargement =findViewById(R.id.BT_emargement);
        if (professeur.isHasSigned()){
            BT_signature_prof.setVisibility(View.GONE);
        }
        else{
            BT_emargement.setEnabled(false);
        }
    }

    public void GoEmargement(View view){
        Intent intent=new Intent(this,AppelActivity.class);
        intent.putExtra("professeur", professeur);
        startActivity(intent);
    }

    public void GoSignature(View view){
        Intent intent=new Intent(this,SignatureActivity.class);
        intent.putExtra("professeur", professeur);
        startActivity(intent);
    }
}