package com.iut.james_mobile.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Professeur implements Serializable {

    private int idProfesseur;

    private Personne personne;

    private Contact contact;

    private Set<Formation> formations;

    private boolean hasSigned;

    private String signature;

    public Professeur(@JsonProperty("idProfesseur") int idProfesseur,
                      @JsonProperty("personne") Personne personne,
                      @JsonProperty("contact") Contact contact,
                      @JsonProperty("formations") Set<Formation> formations,
                      @JsonProperty("hasSigned") boolean hasSigned) {
        this.idProfesseur = idProfesseur;
        this.personne = personne;
        this.contact = contact;
        this.formations = formations;
        this.hasSigned = hasSigned;
    }

    public Professeur(JSONObject json) throws JSONException, ParseException {
        this.idProfesseur = json.getInt("idProfesseur");
        this.hasSigned = json.getBoolean("hasSigned");
        JSONObject jsonPersonne = json.getJSONObject("personne");
        this.personne = new Personne(jsonPersonne);
        JSONObject jsonContact = json.getJSONObject("contact");
        this.contact = new Contact(jsonContact);
        JSONArray jsonFormations = json.getJSONArray("formations");
        this.formations = new HashSet<>();
        for (int i = 0; i < jsonFormations.length(); i++) {
            this.formations.add(new Formation(jsonFormations.getJSONObject(i)));
        }

    }

    public String toString() {
        return "Mon id de professeur" + idProfesseur + "\n"
                + personne.toString() + contact.toString() + this.formations.toString();
    }

}
