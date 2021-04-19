package com.iut.james_mobile.services;

import com.iut.james_mobile.models.Professeur;

import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

public class ServiceProfesseur extends ServiceConfiguration {

    public Professeur readByLoginAndPassword(String login, String password) throws JSONException, IOException {
        //préparation JSON
        JSONObject jsonLogin = new JSONObject();
        jsonLogin.put("email", login);
        jsonLogin.put("password", password);
        //
        StringEntity se = new StringEntity(jsonLogin.toString(), "UTF-8");
        this.prepareHttpPost("/rest/api/professeur/correctLogin", se);
        httpClient = new DefaultHttpClient(this.getHttpParams());
        response = httpClient.execute(httpPost);
        try {
            return objectMapper.readValue(response.getEntity().getContent(), Professeur.class);
        } catch (IOException | IllegalStateException e) {
            return null;
        }
    }

    public Professeur update(Professeur professeur) throws IOException {
        StringEntity se = new StringEntity(objectMapper.writeValueAsString(professeur), "UTF-8");
        this.prepareHttpPost("/rest/api/professeur/update", se);
        httpClient = new DefaultHttpClient(this.getHttpParams());
        response = httpClient.execute(httpPost);
        return objectMapper.readValue(response.getEntity().getContent(), Professeur.class);
    }

}
