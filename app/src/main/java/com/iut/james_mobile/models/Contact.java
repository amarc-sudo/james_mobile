package com.iut.james_mobile.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contact implements Serializable {

    private int idContact;

    private String adresseMail;

    public Contact(int idContact, String adresseMail) {
        this.idContact = idContact;
        this.adresseMail = adresseMail;
    }

    public Contact(JSONObject jsonContact) throws JSONException {
        this.idContact = jsonContact.getInt("idContact");
        this.adresseMail = jsonContact.getString("adresseMail");
    }

    public String toString() {
        return "Mon id de contact est: " + idContact + "\n"
                + "Mon adresse mail est : " + adresseMail + "\n";
    }
}
