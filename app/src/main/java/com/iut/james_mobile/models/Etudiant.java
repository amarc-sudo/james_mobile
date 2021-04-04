package com.iut.james_mobile.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    private Integer nombreRetards;

    private Integer nombreAbsences;

    private String signature;

    @JsonIgnore
    private int positionSpinner;

    public Etudiant(@JsonProperty("idEtudiant") int idEtudiant,
                    @JsonProperty("personne") Personne personne,
                    @JsonProperty("hasSigned") boolean hasSigned,
                    @JsonProperty("formation") Formation formation,
                    @JsonProperty("groupe") Integer groupe,
                    @JsonProperty("nombreRetards") Integer nombreRetards,
                    @JsonProperty("nombreAbsences") Integer nombreAbsences) {
        this.idEtudiant = idEtudiant;
        this.personne = personne;
        this.hasSigned = hasSigned;
        this.formation = formation;
        this.groupe = groupe;
        this.nombreAbsences = nombreAbsences;
        this.nombreRetards = nombreRetards;
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
