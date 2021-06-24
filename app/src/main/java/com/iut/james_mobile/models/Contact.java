package com.iut.james_mobile.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Contact implements Serializable {

    private int idContact;

    private String adresseMail;

    private Compte compte;

    public Contact(@JsonProperty("idContact") int idContact,@JsonProperty("adresseMail") String adresseMail,@JsonProperty("compte") Compte compte) {
        this.idContact = idContact;
        this.adresseMail = adresseMail;
        this.compte = compte;
    }

    public Contact(JSONObject jsonContact) throws JSONException {
        this.idContact = jsonContact.getInt("idContact");
        this.adresseMail = jsonContact.getString("adresseMail");
        this.compte = new Compte(jsonContact.getJSONObject("compte"));
    }

    public String toString() {
        return "Mon id de contact est: " + idContact + "\n"
                + "Mon adresse mail est : " + adresseMail + "\n";
    }
}
