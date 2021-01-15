package com.iut.james_mobile.services;

import com.iut.james_mobile.models.Matiere;
import com.iut.james_mobile.models.Professeur;

import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class ServiceCours extends ServiceConfiguration {

    public void create(Professeur professeur, Matiere matiere, String debutCours, String finCours, Map<Integer, Integer> map) throws JSONException, IOException {
        //préparation JSON
        JSONObject jsonCours = new JSONObject();
        jsonCours.put("professeur", professeur.getIdProfesseur());
        jsonCours.put("matiere", matiere.getIdMatiere());
        jsonCours.put("debut", debutCours);
        jsonCours.put("fin", finCours);
        for (Iterator i = map.keySet().iterator(); i.hasNext(); ) {
            Integer idEtudiant = (Integer) i.next();
            jsonCours.put(Integer.toString(idEtudiant), map.get(idEtudiant));
        }
        //
        StringEntity se = new StringEntity(jsonCours.toString());
        this.prepareHttpPost("/rest/api/cours/addCours", se);
        httpClient = new DefaultHttpClient(this.getHttpParams());
        response = httpClient.execute(httpPost);
    }

}
