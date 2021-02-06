package com.iut.james_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.iut.james_mobile.models.Professeur;
import com.iut.james_mobile.services.ServiceContact;

import org.json.JSONException;

import java.io.IOException;

public class PasswordChangeActivity extends AppCompatActivity {

    private EditText ET_passwordFirst,ET_passwordVerif;
    private ServiceContact serviceContact;
    private Professeur professeur;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordchange);
        ET_passwordFirst=findViewById(R.id.passwordFirst);
        ET_passwordVerif=findViewById(R.id.passwordVerif);
        professeur= (Professeur) getIntent().getSerializableExtra("professeur");
        serviceContact =new ServiceContact();
        sharedPreferences=this.getSharedPreferences("com.iut.james_mobile", Context.MODE_PRIVATE);
    }

    public void passwordChange(View view) throws IOException, JSONException {
        String passwordFirst=ET_passwordFirst.getText().toString();
        String passwordConfirmation=ET_passwordVerif.getText().toString();
        if(passwordFirst.equals(passwordConfirmation)){
            String messageRetour=serviceContact.updatePassword(professeur.getContact().getIdContact(), passwordConfirmation);
            sharedPreferences.edit().putString("password", passwordConfirmation).apply();
            Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.messageServeur) + messageRetour, Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this, ParametreActivity.class);
            intent.putExtra("professeur", professeur);
            finish();
            startActivity(intent);
        }
        else{
            Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.mdpCorrespondentPas), Toast.LENGTH_SHORT).show();
        }
    }
}
