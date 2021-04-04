package com.iut.james_mobile.services;

import com.iut.james_mobile.models.Cours;
import com.iut.james_mobile.models.Etudiant;
import com.iut.james_mobile.models.Matiere;
import com.iut.james_mobile.models.Presence;
import com.iut.james_mobile.models.Professeur;

import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class ServicePresence extends ServiceConfiguration{

    public void create(List<Etudiant> etudiants, Cours cours) throws IOException {
        //préparation JSON
        List<Presence> presences = new ArrayList<>();
        for(Etudiant etudiant: etudiants) {
            presences.add(new Presence(etudiant, cours));
        }
        System.out.println(presences.toString());
        StringEntity se = new StringEntity(objectMapper.writeValueAsString(presences), "UTF-8");
        this.prepareHttpPost("/rest/api/presence/createList", se);
        httpClient = new DefaultHttpClient(this.getHttpParams());
        System.out.println(objectMapper.writeValueAsString(presences));
        response = httpClient.execute(httpPost);
    }
}
