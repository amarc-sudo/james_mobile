package com.iut.james_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iut.james_mobile.apiobject.Professeur;
import com.iut.james_mobile.serviceApi.ServiceLogin;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONException;

public class LoginActivity extends AppCompatActivity  {

    private Button boutonValider;

    private EditText ET_login;

    private EditText ET_password;

    private TextView textMessage;

    private ServiceLogin serviceLogin;

    private SharedPreferences sharedPreferences;

    private CheckBox CB_souvenir;

    private Professeur correctProfesseur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            AnimationDrawable animDrawable = (AnimationDrawable) findViewById(R.id.layout).getBackground();
            animDrawable.setEnterFadeDuration(10);
            animDrawable.setExitFadeDuration(5000);
            animDrawable.start();
            ET_login =  findViewById(R.id.ET_login);
            ET_password =(EditText)this.findViewById(R.id.ET_password);
            boutonValider=(Button)findViewById(R.id.BT_connect);
            CB_souvenir =(CheckBox)this.findViewById(R.id.CB_souvenir);
            ET_login.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                    if (keyEvent.getAction() == KeyEvent.ACTION_DOWN)
                    {
                        switch (keyCode)
                        {
                            case KeyEvent.KEYCODE_ENTER:
                                ET_password.requestFocus();
                                return true;
                            default:
                                break;
                        }
                    }
                    return false;
                }
            });
            ET_password.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                    if (keyEvent.getAction() == KeyEvent.ACTION_DOWN)
                    {
                        switch (keyCode)
                        {
                            case KeyEvent.KEYCODE_ENTER:
                                closeKeyboard();

                                return true;
                            default:
                                break;
                        }
                    }
                    return false;
                }
            });

            sharedPreferences = this.getSharedPreferences("com.iut.james_mobile", Context.MODE_PRIVATE);
            if(sharedPreferences.getBoolean("isChecked",false)){
                ET_login.setText(sharedPreferences.getString("login",""));
                ET_password.setText(sharedPreferences.getString("password",""));
                CB_souvenir.setChecked(true);
            }



        }
        serviceLogin=new ServiceLogin();


    }
    public void Go(){
        Intent intent=new Intent(this,AppelActivity.class);
        intent.putExtra("professeur", correctProfesseur);
        startActivity(intent);
    }




    public void toConnect(View view){
        try {
            String login= ET_login.getText().toString();
            String password= ET_password.getText().toString();
            this.correctProfesseur=serviceLogin.correctLoginAndPassword(login,password);
            if(correctProfesseur != null) {
                if (CB_souvenir.isChecked()) {
                    sharedPreferences.edit().putString("login", ET_login.getText().toString()).apply();
                    sharedPreferences.edit().putString("password", ET_password.getText().toString()).apply();
                    sharedPreferences.edit().putBoolean("isChecked", true).apply();
                }
                else {
                    sharedPreferences.edit().remove("login").apply();
                    sharedPreferences.edit().remove("password").apply();
                    sharedPreferences.edit().remove("isChecked").apply();
                }
                Go();
            }
            else{
                Toast.makeText(this, "Login ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Problème de communication avec le serveur", Toast.LENGTH_SHORT).show();

        }
    }//toConnect

    private void closeKeyboard(){
        // this will give us the view
        // which is currently focus
        // in this layout
        View view = this.getCurrentFocus();

        // if nothing is currently
        // focus then this will protect
        // the app from crash
        if (view != null) {

            // now assign the system
            // service to InputMethodManager
            InputMethodManager manager
                    = (InputMethodManager)
                    getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            manager
                    .hideSoftInputFromWindow(
                            view.getWindowToken(), 0);
        }
    }
}