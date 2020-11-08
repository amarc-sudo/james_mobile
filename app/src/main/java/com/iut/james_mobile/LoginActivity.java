package com.iut.james_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.iut.james_mobile.serviceApi.ServiceLogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private Button boutonValider;

    private EditText fieldLogin;

    private EditText fieldPassword;

    private ServiceLogin serviceLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        serviceLogin=new ServiceLogin();
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            fieldLogin=(EditText)this.findViewById(R.id.email);
            fieldPassword=(EditText)this.findViewById(R.id.password);
            boutonValider=(Button)findViewById(R.id.validLogin);
            boutonValider.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    try {
                        String login= fieldLogin.getText().toString();
                        String password=fieldPassword.getText().toString();
                        boolean correctProfesseur=serviceLogin.correctLoginAndPassword(login,password);
                        System.out.println(correctProfesseur);
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}