package com.iut.james_mobile.apiobject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public Professeur(JSONObject json) throws JSONException, ParseException {
        this.idProfesseur=json.getInt("idProfesseur");
        JSONObject jsonPersonne=json.getJSONObject("personne");
        this.personne= new Personne(jsonPersonne);
        JSONObject jsonContact=json.getJSONObject("contact");
        this.contact=new Contact(jsonContact);
        JSONArray jsonFormations=json.getJSONArray("formations");
        this.formations=new HashSet<>();
        for (int i=0;i<jsonFormations.length();i++){
            this.formations.add(new Formation(jsonFormations.getJSONObject(i)));
        }

    }

    public String toString(){
        return "Mon id de professeur"+idProfesseur+"\n"
                +personne.toString() + contact.toString() + this.formations.toString();
    }

}
