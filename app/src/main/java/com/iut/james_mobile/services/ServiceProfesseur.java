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
        StringEntity se = new StringEntity(jsonLogin.toString());
        this.prepareHttpPost("/rest/api/professeur/correctLogin", se);
        httpClient = new DefaultHttpClient(this.getHttpParams());
        response = httpClient.execute(httpPost);
        InputStream instream = response.getEntity().getContent();
        String result = convertStreamToString(instream);
        try {
            JSONObject jsonProfesseur = new JSONObject(result);
            Professeur professeur = new Professeur(jsonProfesseur);
            return professeur;
        } catch (JSONException | ParseException e) {
            return null;
        }
    }

    public void updateSignature(Professeur professeur, String signature) throws JSONException, IOException {
        JSONObject jsonSignature = new JSONObject();
        jsonSignature.put("professeur", professeur.getIdProfesseur());
        jsonSignature.put("signature", signature);
        StringEntity se = new StringEntity(jsonSignature.toString());
        this.prepareHttpPost("/rest/api/professeur/signature", se);
        httpClient = new DefaultHttpClient(this.getHttpParams());
        response = httpClient.execute(httpPost);
    }


}
