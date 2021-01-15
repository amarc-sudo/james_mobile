package com.iut.james_mobile.services;

import com.iut.james_mobile.models.Etudiant;
import com.iut.james_mobile.models.Formation;
import com.iut.james_mobile.models.Professeur;

import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ServiceEtudiant extends ServiceConfiguration {

    public List<Etudiant> listByProfesseur(Professeur professeur) throws JSONException, IOException {
        Set<Formation> formations = professeur.getFormations();
        JSONObject jsonFormation = new JSONObject();
        int compteur = 0;
        for (Iterator<Formation> i = formations.iterator(); i.hasNext(); ) {
            Formation formation = i.next();
            jsonFormation.put(Integer.toString(compteur), formation.getIdFormation());
            compteur++;
        }
        StringEntity se = new StringEntity(jsonFormation.toString());
        this.prepareHttpPost("/rest/api/etudiant/listEtudiant", se);
        httpClient = new DefaultHttpClient(this.getHttpParams());
        response = httpClient.execute(httpPost);
        InputStream instream = response.getEntity().getContent();
        String result = convertStreamToString(instream);
        try {
            JSONArray jsonEtudiants = new JSONArray(result);
            return convertJSONtoListEtudiants(jsonEtudiants);
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateSignature(Etudiant etudiant, String signature) throws JSONException, IOException {
        JSONObject jsonSignature = new JSONObject();
        jsonSignature.put("etudiant", etudiant.getIdEtudiant());
        jsonSignature.put("signature", signature);
        StringEntity se = new StringEntity(jsonSignature.toString());
        this.prepareHttpPost("/rest/api/etudiant/signature", se);
        httpClient = new DefaultHttpClient(this.getHttpParams());
        response = httpClient.execute(httpPost);
    }


    private List<Etudiant> convertJSONtoListEtudiants(JSONArray jsonEtudiants) throws JSONException, ParseException {
        List<Etudiant> etudiantList = new ArrayList<>();
        for (int i = 0; i < jsonEtudiants.length(); i++) {
            etudiantList.add(new Etudiant(jsonEtudiants.getJSONObject(i)));
        }
        return etudiantList;
    }

}
