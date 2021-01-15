package com.iut.james_mobile.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

@Data
public class Personne implements Serializable {

    private int idPersonne;

    private String nom;

    private String prenom;

    private Date dateCreation;

    public Personne(int idPersonne, String nom, String prenom, Date dateCreation) {
        this.idPersonne = idPersonne;
        this.nom = nom;
        this.prenom = prenom;
        this.dateCreation = dateCreation;
    }

    public Personne(JSONObject jsonPersonne) throws JSONException, ParseException {
        this.idPersonne = jsonPersonne.getInt("idPersonne");
        this.nom = jsonPersonne.getString("nom");
        this.prenom = jsonPersonne.getString("prenom");
        this.dateCreation = new SimpleDateFormat("yyyy-MM-dd").parse((String) jsonPersonne.get("dateCreation"));
    }

    public String toString() {
        return "Je suis " + nom + " " + prenom + " qui a pour id de personne" + idPersonne + "\n"
                + "J'ai ete crée le " + dateCreation.toString() + "\n";

    }

}
