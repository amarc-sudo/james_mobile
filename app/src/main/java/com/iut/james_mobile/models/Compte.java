package com.iut.james_mobile.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import lombok.Data;

@Data
public class Compte implements Serializable {

    private Integer idCompte;

    public Compte(@JsonProperty("idCompte") Integer idCompte) {
        this.idCompte = idCompte;
    }

    public Compte(JSONObject jsonObject) throws JSONException {
        this.idCompte = jsonObject.getInt("idCompte");
    }
}
