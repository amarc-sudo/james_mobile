package com.iut.james_mobile.apiobject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.Normalizer;
import java.text.ParseException;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class Etudiant implements Serializable {
    private int idEtudiant;

    private Personne personne;

    private boolean signature;

    private Formation formation;

    private Integer groupe;

    public Etudiant(int idEtudiant, Personne personne, boolean signature, Formation formation, Integer groupe){
        this.idEtudiant=idEtudiant;
        this.personne=personne;
        this.signature=signature;
        this.formation=formation;
        this.groupe=groupe;
    }

    public Etudiant(JSONObject jsonObject) throws JSONException, ParseException {
        this.idEtudiant=jsonObject.getInt("idEtudiant");
        JSONObject jsonPersonne=jsonObject.getJSONObject("personne");
        this.personne=new Personne(jsonPersonne);
        this.signature= Boolean.parseBoolean(jsonObject.getString("signature"));
        JSONObject jsonFormation=jsonObject.getJSONObject("formation");
        this.formation=new Formation(jsonFormation);
        try{
            this.groupe=jsonObject.getInt("groupe");
        }
        catch(Exception e){
            this.groupe=null;
        }

    }
}
