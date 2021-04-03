package com.iut.james_mobile.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iut.james_mobile.models.Etudiant;
import com.iut.james_mobile.models.Formation;
import com.iut.james_mobile.models.Matiere;
import com.iut.james_mobile.models.Professeur;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

abstract public class ServiceConfiguration {

    protected HttpPost httpPost;

    protected HttpClient httpClient;

    protected HttpResponse response;

    protected String urlLocal = "http://10.0.2.2:8080";

    protected String urlProd = "http://146.59.234.40:8080";

    protected ObjectMapper objectMapper = new ObjectMapper();

    public HttpParams getHttpParams() {
        HttpParams httpParameters = new BasicHttpParams();
        int timeoutConnection = 10000;
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        int timeoutSocket = 10000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        return httpParameters;
    }

    public void prepareHttpPost(String urlAPI, StringEntity se) {
        httpPost = new HttpPost(urlLocal + urlAPI);
        httpPost.setEntity(se);
        httpPost.setHeader("Content-type", "application/json");
    }


    protected String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
