package com.iut.james_mobile.apiobject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class Formation implements Serializable {

    private int idFormation;

    private String intitule;

    public Formation(int idFormation,String intitule){
        this.idFormation=idFormation;
        this.intitule=intitule;
    }

    public Formation(JSONObject jsonFormation) throws JSONException {
        this.idFormation=jsonFormation.getInt("idFormation");
        this.intitule=jsonFormation.getString("intitule");
    }


    @Override
    public String toString() {
        return "Je suis en "+this.intitule+"\n";
    }
}
