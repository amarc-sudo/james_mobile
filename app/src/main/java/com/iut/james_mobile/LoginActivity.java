package com.iut.james_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.iut.james_mobile.serviceApi.ServiceLogin;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button boutonValider;

    private EditText fieldLogin;

    private EditText fieldPassword;

    private TextView textMessage;

    private ServiceLogin serviceLogin;

    private CheckBox checkSouvenir;
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
            textMessage=(TextView) this.findViewById(R.id.message);
            boutonValider.setOnClickListener(this);
            boutonValider.setTag("validationLogin");
            checkSouvenir=(CheckBox)this.findViewById(R.id.souvenir);
            checkSouvenir.setChecked(true);
            try {
                fieldLogin.setText(readFile());
            } catch (IOException e) {
                fieldLogin.setText("");
            }

        }
    }

    public void Go(){
        Intent intent=new Intent(this,AppelActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if ((String)v.getTag()=="validationLogin"){
            try {
                String login= fieldLogin.getText().toString();
                String password=fieldPassword.getText().toString();
                boolean correctProfesseur=serviceLogin.correctLoginAndPassword(login,password);
                if(correctProfesseur){
                    if (checkSouvenir.isChecked())
                        writeFile(login);
                    else
                        writeFile("");
                    Go();
                }
                else{
                    textMessage.setText("Login ou mot de passe incorrect");
                }
            } catch (IOException | JSONException e) {
                textMessage.setText("Problème de communication avec le serveur");
            }

        }
    }

    private void writeFile(String login) throws IOException {
        FileOutputStream fOut = null;
        fOut=openFileOutput("sauvegarde.txt",Context.MODE_PRIVATE);
        fOut.write(login.getBytes());
        fOut.close();

    }

    private String readFile() throws IOException {
        FileInputStream fileInputStream=null;
        String login;
        fileInputStream=openFileInput("sauvegarde.txt");
        InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
        return bufferedReader.readLine();
    }
}