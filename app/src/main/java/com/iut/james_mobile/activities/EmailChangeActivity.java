package com.iut.james_mobile.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.iut.james_mobile.R;
import com.iut.james_mobile.models.Contact;
import com.iut.james_mobile.models.Professeur;
import com.iut.james_mobile.services.ServiceContact;

import org.json.JSONException;

import java.io.IOException;

public class EmailChangeActivity extends AppCompatActivity {

    private EditText ET_emailChange, ET_emailChangeConfirmation;
    private ServiceContact serviceContact;
    private Professeur professeur;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emailchange);
        ET_emailChange = findViewById(R.id.ET_emailChange);
        ET_emailChangeConfirmation = findViewById(R.id.ET_emailChangeConfirmation);
        professeur = (Professeur) getIntent().getSerializableExtra("professeur");
        serviceContact = new ServiceContact();
        sharedPreferences = this.getSharedPreferences("com.iut.james_mobile", Context.MODE_PRIVATE);
    }

    public void emailChange(View view) throws IOException, JSONException {
        String email = ET_emailChange.getText().toString();
        String emailConfirmation = ET_emailChangeConfirmation.getText().toString();
        if (email.equals(emailConfirmation) && !email.isEmpty() && !emailConfirmation.isEmpty()) {
            Contact updatedContact = serviceContact.update(professeur.getContact());
            String messageRetour = updatedContact != null ? " Modification bien prise en compte" : " L'adresse mail est déjà prise";
            if (updatedContact != null)
                professeur.setContact(updatedContact);
            sharedPreferences.edit().putString("login", emailConfirmation).apply();
            Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.messageServeur) + messageRetour, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ParametreActivity.class);
            intent.putExtra("professeur", professeur);
            finish();
            startActivity(intent);
        } else {
            Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.emailCorrespondentPas), Toast.LENGTH_SHORT).show();
        }
    }

}
