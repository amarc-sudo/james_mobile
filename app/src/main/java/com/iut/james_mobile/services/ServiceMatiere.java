package com.iut.james_mobile.services;

import com.iut.james_mobile.models.Formation;
import com.iut.james_mobile.models.Matiere;
import com.iut.james_mobile.models.Professeur;

import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ServiceMatiere extends ServiceConfiguration {

    public List<Matiere> listByProfesseur(Professeur professeur) throws IOException, JSONException {
        Set<Formation> formations = professeur.getFormations();
        JSONObject jsonFormation = new JSONObject();
        int compteur = 0;
        for (Iterator<Formation> i = formations.iterator(); i.hasNext(); ) {
            Formation formation = i.next();
            jsonFormation.put(Integer.toString(compteur), formation.getIdFormation());
            compteur++;
        }
        StringEntity se = new StringEntity(jsonFormation.toString());
        this.prepareHttpPost("/rest/api/matiere/listMatiere", se);
        httpClient = new DefaultHttpClient(this.getHttpParams());
        response = httpClient.execute(httpPost);
        InputStream instream = response.getEntity().getContent();
        String result = convertStreamToString(instream);
        try {
            JSONArray jsonMatieres = new JSONArray(result);
            return convertJSONtoListMatieres(jsonMatieres);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Matiere> convertJSONtoListMatieres(JSONArray jsonMatieres) throws JSONException {
        List<Matiere> matiereList = new ArrayList<>();
        for (int i = 0; i < jsonMatieres.length(); i++) {
            matiereList.add(new Matiere(jsonMatieres.getJSONObject(i)));
        }
        return matiereList;
    }

}
