package com.iut.james_mobile.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class Etudiant implements Serializable {

    private int idEtudiant;

    private Personne personne;

    private boolean hasSigned;

    private Formation formation;

    private Integer groupe;

    private int positionSpinner;

    public Etudiant(int idEtudiant, Personne personne, boolean signature, Formation formation, Integer groupe) {
        this.idEtudiant = idEtudiant;
        this.personne = personne;
        this.hasSigned = signature;
        this.formation = formation;
        this.groupe = groupe;
        this.positionSpinner = 0;
    }

    public Etudiant(JSONObject jsonObject) throws JSONException, ParseException {
        this.idEtudiant = jsonObject.getInt("idEtudiant");
        JSONObject jsonPersonne = jsonObject.getJSONObject("personne");
        this.personne = new Personne(jsonPersonne);
        this.hasSigned = jsonObject.getBoolean("hasSigned");
        JSONObject jsonFormation = jsonObject.getJSONObject("formation");
        this.formation = new Formation(jsonFormation);
        try {
            this.groupe = jsonObject.getInt("groupe");
        } catch (Exception e) {
            this.groupe = null;
        }
        this.positionSpinner = 0;
    }
}
