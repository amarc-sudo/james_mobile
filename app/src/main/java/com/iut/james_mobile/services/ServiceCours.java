package com.iut.james_mobile.services;

import com.iut.james_mobile.models.Cours;
import com.iut.james_mobile.models.Matiere;
import com.iut.james_mobile.models.Professeur;

import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.sql.Time;


public class ServiceCours extends ServiceConfiguration {

    public Cours create(Professeur professeur, Matiere matiere, String debutCours, String finCours) throws IOException {
        //préparation JSON
        Cours cours = new Cours(matiere, Time.valueOf(debutCours), Time.valueOf(finCours), professeur);
        StringEntity se = new StringEntity(objectMapper.writeValueAsString(cours), "UTF-8");
        this.prepareHttpPost("/rest/api/cours/create", se);
        httpClient = new DefaultHttpClient(this.getHttpParams());
        response = httpClient.execute(httpPost);
        return objectMapper.readValue(response.getEntity().getContent(), Cours.class);
    }

}
