package com.iut.james_mobile;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.iut.james_mobile.models.Etudiant;
import com.iut.james_mobile.models.Formation;
import com.iut.james_mobile.models.Matiere;
import com.iut.james_mobile.models.Professeur;
import com.iut.james_mobile.services.ServiceCours;
import com.iut.james_mobile.services.ServiceEtudiant;
import com.iut.james_mobile.services.ServiceMatiere;
import com.iut.james_mobile.views.RecyclerSimpleViewAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import lombok.SneakyThrows;

public class AppelActivity extends AppCompatActivity {

    private List<Matiere> matiereList;

    private List<Etudiant> etudiantList;

    private List<Etudiant> displayedEtudiantList;

    private ServiceEtudiant serviceEtudiant;

    private ServiceMatiere serviceMatiere;

    private ServiceCours serviceCours;

    private Spinner SP_matiere;

    private Spinner SP_formation;

    private Professeur professeur;

    private String intituleFormationSelectionne;

    private TimePicker TP_debut;

    private TimePicker TP_fin;

    private RecyclerView recyclerView;

    private Button BT_validation;

    private RecyclerSimpleViewAdapter adapter;

    private String heureDebut;

    private String heureFin;

    private List<String> nomsFormations;

    private SharedPreferences sharedPreferences;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceEtudiant = new ServiceEtudiant();
        serviceMatiere = new ServiceMatiere();
        serviceCours = new ServiceCours();
        setContentView(R.layout.activity_appel);
        Intent intent = getIntent();
        professeur = (Professeur) intent.getSerializableExtra("professeur");
        sharedPreferences = this.getSharedPreferences("com.iut.james_mobile", Context.MODE_PRIVATE);
        matiereList = serviceMatiere.listByProfesseur(professeur);
        etudiantList = serviceEtudiant.listByProfesseur(professeur);
        SP_matiere = findViewById(R.id.SP_matiere);
        SP_formation = findViewById(R.id.SP_formation);
        nomsFormations = new ArrayList<>();
        for (Formation formation : professeur.getFormations()) {
            nomsFormations.add(formation.getIntitule());
            nomsFormations.add(formation.getIntitule() + "-1");
            nomsFormations.add(formation.getIntitule() + "-2");
        }
        Collections.sort(nomsFormations);
        SP_formation.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item, nomsFormations));
        SP_formation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                intituleFormationSelectionne = (String) SP_formation.getSelectedItem();
                setValueSpinnerMatiere();
                setDisplayedEtudiants();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        intituleFormationSelectionne = (String) intent.getStringExtra("formation");
        if (intituleFormationSelectionne == null)
            intituleFormationSelectionne = (String) SP_formation.getSelectedItem();
        else {
            SP_formation.setSelection(getPositionFormation(intituleFormationSelectionne));
        }

        this.setValueSpinnerMatiere();
        TP_debut = (TimePicker) findViewById(R.id.TP_debut);
        TP_debut.setIs24HourView(true);
        TP_debut.setMinute(0);
        TP_debut.setHour((Calendar.getInstance()).get(Calendar.HOUR_OF_DAY));
        TP_fin = (TimePicker) findViewById(R.id.TP_fin);
        TP_fin.setIs24HourView(true);
        TP_fin.setMinute(0);
        TP_fin.setHour((Calendar.getInstance()).get(Calendar.HOUR_OF_DAY) + sharedPreferences.getInt("dureeCours", 1));
        this.recyclerView = (RecyclerView) findViewById(R.id.RV_eleve);
        this.setDisplayedEtudiants();
        BT_validation = findViewById(R.id.BT_validation);
    }

    @Override
    public void onResume() {
        super.onResume();
        LanguageModifier languageModifier = new LanguageModifier();
        languageModifier.setLanguage(sharedPreferences.getString("language", "fr"), this);
    }

    private int getPositionFormation(String intituleFormationSelectionne) {
        int compteur = 0;
        for (String intitule : nomsFormations) {
            if (intituleFormationSelectionne.equals(intitule)) {
                return compteur;
            }
            compteur++;
        }
        return 0;
    }

    private Integer getIdPresence(String selectedItem) {
        switch (selectedItem) {
            case "Présent":
                return 1;
            case "En Retard":
                return 2;
            case "Absent":
                return 3;
        }
        return null;
    }

    public void setValueSpinnerMatiere() {
        List<String> displayedMatiere = new ArrayList<String>();
        if (matiereList != null) {
            for (Matiere matiere : matiereList) {
                if (matiere.getFormation().getIntitule().equals(this.getIntituleFormation())) {
                    displayedMatiere.add(matiere.getIntitule());
                }
            }
        }
        SP_matiere.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item, displayedMatiere));

    }

    public void setDisplayedEtudiants() {
        List<Etudiant> displayedEtudiants = new ArrayList<>();
        if (etudiantList != null) {
            for (Etudiant etudiant : etudiantList) {
                if (getNumeroGroupe() == null) {
                    if (etudiant.getFormation().getIntitule().equals(getIntituleFormation())) {
                        displayedEtudiants.add(etudiant);
                    }
                }
                if (etudiant.getFormation().getIntitule().equals(getIntituleFormation()) && etudiant.getGroupe() == getNumeroGroupe()) {
                    displayedEtudiants.add(etudiant);
                }
            }
        }
        this.displayedEtudiantList = displayedEtudiants;
        adapter = new RecyclerSimpleViewAdapter(displayedEtudiants, android.R.layout.simple_list_item_1);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setProfesseurConnecte(professeur);
        adapter.setFormationSelectionne(intituleFormationSelectionne);
    }

    public String getIntituleFormation() {
        StringTokenizer tokenizer = new StringTokenizer(intituleFormationSelectionne, "-");
        String token;
        String intituleFormation = new String();
        while (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();
            if (isInt(token) == true) {
                break;
            }
            intituleFormation += token;
            intituleFormation += "-";
        }
        return intituleFormation.substring(0, intituleFormation.length() - 1);
    }

    public boolean isInt(String string) {
        char[] s = string.toCharArray();
        for (int i = 0; i < s.length; i++) {
            if (!Character.isDigit(s[i])) {
                return false;
            }
        }
        return true;
    }

    public Integer getNumeroGroupe() {
        StringTokenizer tokenizer = new StringTokenizer(intituleFormationSelectionne, "-");
        String token = new String();
        while (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();
        }
        try {
            return Integer.parseInt(token);
        } catch (Exception e) {
            return null;
        }
    }

    public Matiere getMatiereByIntitule(String intitule) {
        for (Matiere matiere : matiereList) {
            if (matiere.getFormation().getIntitule().equals(getIntituleFormation()) && matiere.getIntitule().equals(intitule)) {
                return matiere;
            }
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void popUpValidation(View v) {
        int nombreAbsents = 0;
        int nombreRetardataires = 0;
        String matiere = (String) SP_matiere.getSelectedItem();
        Map<Etudiant, Spinner> relevePresence = adapter.getSP_presences();
        Map<Integer, Integer> eleveStatus = new HashMap<>();
        if (TP_debut.getMinute() < 10) {
            heureDebut = Integer.toString(TP_debut.getHour()) + ":0" + Integer.toString(TP_debut.getMinute());
        } else {
            heureDebut = Integer.toString(TP_debut.getHour()) + ":" + Integer.toString(TP_debut.getMinute());
        }
        if (TP_fin.getMinute() < 10) {
            heureFin = Integer.toString(TP_fin.getHour()) + ":0" + Integer.toString(TP_fin.getMinute());
        } else {
            heureFin = Integer.toString(TP_fin.getHour()) + ":" + Integer.toString(TP_fin.getMinute());
        }
        boolean toutLeMondeASigne = true;
        for (Iterator<Etudiant> iterator = displayedEtudiantList.iterator(); iterator.hasNext(); ) {
            Etudiant etudiant = iterator.next();
            switch (etudiant.getPositionSpinner()) {
                case 1:
                    nombreRetardataires++;
                    break;
                case 2:
                    nombreAbsents++;
                    break;
            }
            if (!etudiant.isHasSigned()) {
                toutLeMondeASigne = false;
            }
            eleveStatus.put(etudiant.getIdEtudiant(), etudiant.getPositionSpinner() + 1);
        }
        if (toutLeMondeASigne) {
            new AlertDialog.Builder(this)
                    .setTitle(getResources().getString((R.string.confirmationAjoutCours)))
                    .setMessage(getResources().getString(R.string.textMatiere) + " : " + matiere + "\n" +
                            getResources().getString(R.string.heureDebut) + " : " + heureDebut + "\n" +
                            getResources().getString(R.string.heureFin) + " : " + heureFin + "\n" +
                            getResources().getString(R.string.nombreRetardataire) + " : " + nombreRetardataires + "\n" +
                            getResources().getString(R.string.nombreAbsent) + " : " + nombreAbsents + "\n")
                    .setPositiveButton(getResources().getString(R.string.valider), new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @SneakyThrows
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            serviceCours.create(professeur, getMatiereByIntitule(matiere), heureDebut,
                                    heureFin, eleveStatus);
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.coursAjoute), Toast.LENGTH_LONG).show();
                            GoAtHome();
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.annuler), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(AppelActivity.this, getResources().getString(R.string.annulerCoursAjout), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .show();
        } else {
            Toast.makeText(AppelActivity.this, getResources().getString(R.string.tousSigne), Toast.LENGTH_SHORT).show();
        }
    }

    private void GoAtHome() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.putExtra("professeur", professeur);
        startActivity(intent);
    }

}