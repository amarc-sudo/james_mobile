package com.iut.james_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.iut.james_mobile.apiobject.Etudiant;
import com.iut.james_mobile.apiobject.Matiere;
import com.iut.james_mobile.apiobject.Professeur;
import com.iut.james_mobile.serviceApi.ServiceAPI;

import java.util.List;

import lombok.SneakyThrows;

public class AppelActivity extends AppCompatActivity{

    List<Matiere> matiereList;

    List<Etudiant> etudiantList;

    ServiceAPI serviceAPI =new ServiceAPI();

    Professeur professeur;
    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appel);
        Intent intent=getIntent();
        professeur= (Professeur) intent.getSerializableExtra("professeur");
        matiereList= serviceAPI.getMatiereOfProfesseur(professeur);
        etudiantList= serviceAPI.getEtudiantOfProfesseur(professeur);
        Toast.makeText(this, professeur.getPersonne().getNom(), Toast.LENGTH_SHORT).show();
    }
}