package com.iut.james_mobile.serviceApi;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
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

public class ServiceForgotPassword {

    private HttpPost httpPost;

    private HttpClient httpClient;

    private HttpResponse response;

    private String urlLocal = "http://10.0.2.2:8080";

    private String urlProd = "http://146.59.234.40:8080";

    public boolean isEmailCorrect(String email) throws JSONException, IOException {
        JSONObject jsonEmail = new JSONObject();
        jsonEmail.put("email", email);
        httpPost = new HttpPost(urlProd + "/rest/api/contact/resetPassword");
        StringEntity se = new StringEntity(jsonEmail.toString());
        httpPost.setEntity(se);
        httpPost.setHeader("Content-type", "application/json");
        System.out.println(jsonEmail.toString());

        HttpParams httpParameters = new BasicHttpParams();
        int timeoutConnection = 10000;
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        int timeoutSocket = 10000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

        httpClient = new DefaultHttpClient(httpParameters);
        response = httpClient.execute(httpPost);

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String bool = reader.readLine();
        System.out.println(bool);
        return Boolean.parseBoolean(bool);
    }
}
