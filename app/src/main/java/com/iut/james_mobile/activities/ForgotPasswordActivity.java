package com.iut.james_mobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.iut.james_mobile.R;
import com.iut.james_mobile.services.ServiceContact;

import org.json.JSONException;

import java.io.IOException;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Button BT_emailButton;

    private EditText emailForgotPassword;

    private String emailForgotPasswordString;

    private ServiceContact serviceContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        BT_emailButton = (Button) findViewById(R.id.BT_emailButton);
        emailForgotPassword = (EditText) findViewById(R.id.emailForgotPassword);
        serviceContact = new ServiceContact();
    }

    public void getAndSendEmail(View view) throws IOException, JSONException {
        emailForgotPasswordString = emailForgotPassword.getText().toString();
        if (emailForgotPasswordString.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.champsVideEmail), Toast.LENGTH_LONG).show();
        } else {
            boolean email = serviceContact.checkLoginAndSendMailReset(emailForgotPasswordString);
            if (email == true) {
                Toast.makeText(this, getResources().getString(R.string.emailEnvoyeSurEmail), Toast.LENGTH_LONG).show();
                this.finish();
            } else {
                Toast.makeText(this, getResources().getString(R.string.emailPasValide), Toast.LENGTH_LONG).show();
            }
        }
    }

}
