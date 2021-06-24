package com.iut.james_mobile.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class Matiere implements Serializable {

    private int idMatiere;

    private Formation formation;

    private String intitule;

    public Matiere(@JsonProperty("idMatiere") int idMatiere, @JsonProperty("formation")  Formation formation, @JsonProperty("intitule") String intitule) {
        this.idMatiere = idMatiere;
        this.formation = formation;
        this.intitule = intitule;
    }

    public Matiere(JSONObject jsonMatiere) throws JSONException {
        this.idMatiere = jsonMatiere.getInt("idMatiere");
        JSONObject jsonFormation = jsonMatiere.getJSONObject("formation");
        this.formation = new Formation(jsonFormation);
        this.intitule = jsonMatiere.getString("intitule");
    }
}
