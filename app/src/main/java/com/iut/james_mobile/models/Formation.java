package com.iut.james_mobile.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class Formation implements Serializable, Comparable<Formation> {

    private int idFormation;

    private String intitule;

    public Formation(@JsonProperty("idFormation") int idFormation,
                     @JsonProperty("intitule") String intitule) {
        this.idFormation = idFormation;
        this.intitule = intitule;
    }

    public Formation(JSONObject jsonFormation) throws JSONException {
        this.idFormation = jsonFormation.getInt("idFormation");
        this.intitule = jsonFormation.getString("intitule");
    }


    @Override
    public String toString() {
        return "Formation{" +
                "idFormation=" + idFormation +
                ", intitule='" + intitule + '\'' +
                '}';
    }

    @Override
    public int compareTo(Formation o) {
        return this.intitule.compareTo(o.intitule);
    }
}
