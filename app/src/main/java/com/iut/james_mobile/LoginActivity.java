package com.iut.james_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.iut.james_mobile.models.Professeur;
import com.iut.james_mobile.services.ServiceAPI;

import java.io.IOException;

import org.json.JSONException;


public class LoginActivity extends AppCompatActivity  {

    private Button BT_forgotPassword;

    private Button boutonValider;

    private EditText ET_login;

    private EditText ET_password;

    private TextView textMessage;

    private ServiceAPI serviceAPI;

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
            ET_login =  findViewById(R.id.ET_login);
            ET_password =(EditText)this.findViewById(R.id.ET_password);
            boutonValider=(Button)findViewById(R.id.BT_connect);
            CB_souvenir =(CheckBox)this.findViewById(R.id.CB_souvenir);
            BT_forgotPassword=(Button) findViewById(R.id.BT_forgotPassword);
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
        serviceAPI =new ServiceAPI();
    }
    public void Go(){
        Intent intent=new Intent(this,WelcomeActivity.class);
        intent.putExtra("professeur", correctProfesseur);
        this.finish();
        startActivity(intent);
    }

    public void goForgotPassword(View view) {
        Intent forgot=new Intent(this,ForgotPasswordActivity.class);
        startActivity(forgot);
    }

    public void toConnect(View view){
        try {
            String login= ET_login.getText().toString();
            String password= ET_password.getText().toString();
            this.correctProfesseur= serviceAPI.readProfesseurByLoginAndPassword(login,password);
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
                Toast.makeText(this.getApplicationContext(), "Login ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(this.getApplicationContext(), "Problème de communication avec le serveur", Toast.LENGTH_SHORT).show();

        }
    }

    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
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