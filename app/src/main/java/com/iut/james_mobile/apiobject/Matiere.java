package com.iut.james_mobile.apiobject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.Normalizer;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class Matiere implements Serializable {

    private int idMatiere;

    private Formation formation;

    private String intitule;

    public Matiere(int idMatiere, Formation formation, String intitule) {
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
