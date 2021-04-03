package com.iut.james_mobile.services;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
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
        Log.i("prof", professeur.getFormations().toString());
        StringEntity se = new StringEntity(objectMapper.writeValueAsString(professeur.getFormations()));
        this.prepareHttpPost("/rest/api/matiere/listMatiere", se);
        httpClient = new DefaultHttpClient(this.getHttpParams());
        response = httpClient.execute(httpPost);
        try {
            return objectMapper.readValue(response.getEntity().getContent(), new TypeReference<List<Matiere>>() {
            });
        } catch (IOException | IllegalStateException e) {
            return null;
        }
    }
}
