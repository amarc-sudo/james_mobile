package com.iut.james_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.iut.james_mobile.apiobject.Etudiant;
import com.iut.james_mobile.apiobject.Matiere;
import com.iut.james_mobile.apiobject.Professeur;
import com.iut.james_mobile.serviceApi.ServiceLogin;

import java.util.List;

import lombok.SneakyThrows;

public class AppelActivity extends AppCompatActivity{

    List<Matiere> matiereList;

    List<Etudiant> etudiantList;

    ServiceLogin serviceLogin=new ServiceLogin();

    Professeur professeur;
    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appel);
        Intent intent=getIntent();
        professeur= (Professeur) intent.getSerializableExtra("professeur");
        matiereList=serviceLogin.getMatiereOfProfesseur(professeur);
        etudiantList=serviceLogin.getEtudiantOfProfesseur(professeur);
    }
}