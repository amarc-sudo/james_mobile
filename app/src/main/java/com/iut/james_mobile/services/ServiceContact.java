package com.iut.james_mobile.services;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServiceContact extends ServiceConfiguration {

    public boolean checkLoginAndSendMailReset(String email) throws JSONException, IOException {
        JSONObject jsonEmail = new JSONObject();
        jsonEmail.put("email", email);
        httpPost = new HttpPost(urlProd + "/rest/api/contact/resetPassword");
        StringEntity se = new StringEntity(jsonEmail.toString());
        httpPost.setEntity(se);
        httpPost.setHeader("Content-type", "application/json");
        HttpParams httpParameters = new BasicHttpParams();
        int timeoutConnection = 10000;
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        int timeoutSocket = 10000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        httpClient = new DefaultHttpClient(httpParameters);
        response = httpClient.execute(httpPost);
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String bool = reader.readLine();
        return Boolean.parseBoolean(bool);
    }

}
