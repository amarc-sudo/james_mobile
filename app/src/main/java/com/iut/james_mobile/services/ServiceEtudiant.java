package com.iut.james_mobile.services;

import com.fasterxml.jackson.core.type.TypeReference;
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
        StringEntity se = new StringEntity(objectMapper.writeValueAsString(formations), "UTF-8");
        this.prepareHttpPost("/rest/api/etudiant/listByFormation", se);
        httpClient = new DefaultHttpClient(this.getHttpParams());
        response = httpClient.execute(httpPost);
        try {
            return objectMapper.readValue(response.getEntity().getContent(), new TypeReference<List<Etudiant>>() {
            });
        } catch (IOException | IllegalStateException e) {
            return null;
        }
    }

    public Etudiant update(Etudiant etudiant) throws IOException {
        StringEntity se = new StringEntity(objectMapper.writeValueAsString(etudiant), "UTF-8");
        this.prepareHttpPost("/rest/api/etudiant/update", se);
        httpClient = new DefaultHttpClient(this.getHttpParams());
        response = httpClient.execute(httpPost);
        return objectMapper.readValue(response.getEntity().getContent(), Etudiant.class);
    }

}
