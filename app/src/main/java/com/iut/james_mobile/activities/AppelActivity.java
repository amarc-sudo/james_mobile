package com.iut.james_mobile.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.iut.james_mobile.R;
import com.iut.james_mobile.models.Etudiant;
import com.iut.james_mobile.models.Formation;
import com.iut.james_mobile.models.Matiere;
import com.iut.james_mobile.models.Professeur;
import com.iut.james_mobile.services.ServiceCours;
import com.iut.james_mobile.services.ServiceEtudiant;
import com.iut.james_mobile.services.ServiceMatiere;
import com.iut.james_mobile.views.RecyclerSimpleViewAdapter;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import lombok.SneakyThrows;

/**
 * Activité qui permet de faire le relevé des présences d'un cours
 */
public class AppelActivity extends AppCompatActivity {

    /**
     * La liste des matières du professeur
     */
    private List<Matiere> matiereList;

    /**
     * La liste des étudiants du professeur
     */
    private List<Etudiant> etudiantList;

    /**
     * La liste des étudiants du professeur affichés à l'écran
     */
    private List<Etudiant> displayedEtudiantList;

    /**
     * Le spinner qui affiche les matières
     */
    private Spinner SP_matiere;

    /**
     * Le spinner qui affiche les formations
     */
    private Spinner SP_formation;

    /**
     * Le professeur connecté à l'application
     */
    private Professeur professeur;

    /**
     * Le nom de la formation séléctionné par le professeur
     */
    private String intituleFormationSelectionne;

    /**
     * Le time picker permettant de séléctioner l'heure de début du cours
     */
    private TimePicker TP_debut;

    /**
     * Le time picker permettant de séléctionner l'heure de fin du cours
     */
    private TimePicker TP_fin;

    /**
     * Le recycler view qui nous permet d'afficher la liste des élèves
     */
    private RecyclerView recyclerView;

    /**
     * La liste des formations du professeur
     */
    private List<String> nomsFormations;

    /**
     * Méthode onCreate qui est appelé à la création de l'activité et qui va donc mettre
     * en place les données qui pourront être manipulées par l'utilisateur
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ServiceEtudiant serviceEtudiant = new ServiceEtudiant();
        ServiceMatiere serviceMatiere = new ServiceMatiere();
        setContentView(R.layout.activity_appel);
        Intent intent = getIntent();
        professeur = (Professeur) intent.getSerializableExtra("professeur");
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
        SP_formation.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_item, nomsFormations));
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
        intituleFormationSelectionne = intent.getStringExtra("formation");
        if (intituleFormationSelectionne == null)
            intituleFormationSelectionne = (String) SP_formation.getSelectedItem();
        else {
            SP_formation.setSelection(getPositionFormation(intituleFormationSelectionne));
        }

        this.setValueSpinnerMatiere();
        TP_debut = findViewById(R.id.TP_debut);
        TP_debut.setIs24HourView(true);
        TP_debut.setMinute(0);
        TP_debut.setHour((Calendar.getInstance()).get(Calendar.HOUR_OF_DAY));
        TP_fin = findViewById(R.id.TP_fin);
        TP_fin.setIs24HourView(true);
        TP_fin.setMinute(0);
        TP_fin.setHour((Calendar.getInstance()).get(Calendar.HOUR_OF_DAY) + 1);
        this.recyclerView = findViewById(R.id.RV_eleve);
        this.setDisplayedEtudiants();
    }

    /**
     * Méthode qui permet d'obtenir la place dans le spinner des formations à partir de son nom
     * @param intituleFormationSelectionne l'intitulé de la formation que l'on cherche
     * @return la position dans le spinner de la formation
     */
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

    /**
     * Méthode qui met à jour
     */
    public void setValueSpinnerMatiere() {
        List<String> displayedMatiere = new ArrayList<>();
        for (Matiere matiere : matiereList) {
            if (matiere.getFormation().getIntitule().equals(this.getIntituleFormation())) {
                displayedMatiere.add(matiere.getIntitule());
            }
        }
        SP_matiere.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_item, displayedMatiere));
    }

    public void setDisplayedEtudiants() {
        RecyclerSimpleViewAdapter adapter;
        List<Etudiant> displayedEtudiants = new ArrayList<>();
        for (Etudiant etudiant : etudiantList) {
            if (getNumeroGroupe() == null && etudiant.getFormation().getIntitule().equals(getIntituleFormation())) {
                displayedEtudiants.add(etudiant);
            }
            if (etudiant.getFormation().getIntitule().equals(getIntituleFormation()) && etudiant.getGroupe().equals(getNumeroGroupe())) {
                displayedEtudiants.add(etudiant);
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
        String intituleFormation = "";
        while (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();
            if (isInt(token)) {
                break;
            }
            intituleFormation += token;
            intituleFormation += "-";
        }
        return intituleFormation.substring(0, intituleFormation.length() - 1);
    }

    public boolean isInt(String string) {
        char[] s = string.toCharArray();
        for (char c : s) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public Integer getNumeroGroupe() {
        StringTokenizer tokenizer = new StringTokenizer(intituleFormationSelectionne, "-");
        String token = "";
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
        String heureDebut;
        String heureFin;
        String matiere = (String) SP_matiere.getSelectedItem();
        Map<Integer, Integer> eleveStatus = new HashMap<>();
        if (TP_debut.getMinute() < 10) {
            heureDebut = TP_debut.getHour() + ":0" + TP_debut.getMinute();
        } else {
            heureDebut = TP_debut.getHour() + ":" + TP_debut.getMinute();
        }
        if (TP_fin.getMinute() < 10) {
            heureFin = TP_fin.getHour() + ":0" + TP_fin.getMinute();
        } else {
            heureFin = TP_fin.getHour() + ":" + TP_fin.getMinute();
        }
        boolean toutLeMondeASigne = true;
        for (Etudiant etudiant : displayedEtudiantList) {
            if (etudiant.getPositionSpinner() == 1)
                nombreRetardataires++;
            else if (etudiant.getPositionSpinner() == 2)
                nombreAbsents++;
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
                    .setPositiveButton(getResources().getString(R.string.valider), (dialogInterface, i) -> {
                        try {
                            ServiceCours serviceCours = new ServiceCours();
                            serviceCours.create(professeur, getMatiereByIntitule(matiere), heureDebut,
                                    heureFin, eleveStatus);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.coursAjoute), Toast.LENGTH_LONG).show();
                        goAtHome();
                    })
                    .setNegativeButton(getResources().getString(R.string.annuler), (dialogInterface, i) -> Toast.makeText(AppelActivity.this, getResources().getString(R.string.annulerCoursAjout), Toast.LENGTH_SHORT).show())
                    .show();
        } else {
            Toast.makeText(AppelActivity.this, getResources().getString(R.string.tousSigne), Toast.LENGTH_SHORT).show();
        }
    }

    private void goAtHome() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.putExtra("professeur", professeur);
        startActivity(intent);
    }

}