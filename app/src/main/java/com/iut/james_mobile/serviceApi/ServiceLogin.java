package com.iut.james_mobile.serviceApi;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class ServiceLogin {

    private HttpPost httpPost;

    private HttpClient httpClient=new DefaultHttpClient();

    private HttpResponse response;

    public boolean correctLoginAndPassword(String login,String password) throws JSONException, IOException {
        JSONObject jsonLogin=new JSONObject();
        jsonLogin.put("email",login);
        jsonLogin.put("password",password);
        httpPost=new HttpPost("http://10.0.2.2:8080/rest/api/professeur/correctLogin");
        StringEntity se=new StringEntity(jsonLogin.toString());
        httpPost.setEntity(se);
        httpPost.setHeader("Content-type","application/json");
        System.out.println(jsonLogin.toString());
        response= httpClient.execute(httpPost);
        BufferedReader reader=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String bool=reader.readLine();
        return Boolean.parseBoolean(bool);
    }
}
