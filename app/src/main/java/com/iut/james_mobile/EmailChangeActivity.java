package com.iut.james_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.iut.james_mobile.models.Professeur;
import com.iut.james_mobile.services.ServiceContact;

import org.json.JSONException;

import java.io.IOException;

public class EmailChangeActivity extends AppCompatActivity {

    private EditText ET_emailChange,ET_emailChangeConfirmation;
    private Button BT_emailChangeConfirmation;
    private ServiceContact serviceContact;
    private Professeur professeur;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emailchange);
        BT_emailChangeConfirmation = (Button) findViewById(R.id.BT_emailChangeConfirmation);
        ET_emailChange = (EditText) findViewById(R.id.ET_emailChange);
        ET_emailChangeConfirmation = (EditText) findViewById(R.id.ET_emailChangeConfirmation);
        professeur= (Professeur) getIntent().getSerializableExtra("professeur");
        serviceContact =new ServiceContact();
        sharedPreferences=this.getSharedPreferences("com.iut.james_mobile", Context.MODE_PRIVATE);
    }

    public void emailChange(View view) throws IOException, JSONException {
        String email=ET_emailChange.getText().toString();
        String emailConfirmation=ET_emailChangeConfirmation.getText().toString();
        if(email.equals(emailConfirmation) && email.isEmpty()==false && emailConfirmation.isEmpty()==false) {
            String messageRetour=serviceContact.updateMail(professeur.getContact().getIdContact(), emailConfirmation);
            sharedPreferences.edit().putString("login", emailConfirmation).apply();
            Toast.makeText(this.getApplicationContext(), "Message serveur : " + messageRetour, Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this, ParametreActivity.class);
            intent.putExtra("professeur", professeur);
            finish();
            startActivity(intent);
        }
        else{
            Toast.makeText(this.getApplicationContext(), "Les emails ne correspondent pas", Toast.LENGTH_SHORT).show();
        }
    }

}
