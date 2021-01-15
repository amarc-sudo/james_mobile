package com.iut.james_mobile;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class PasswordChangeActivity extends AppCompatActivity {

    private EditText ET_passwordFirst,ET_passwordVerif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordchange);
        ET_passwordFirst=findViewById(R.id.passwordFirst);
        ET_passwordVerif=findViewById(R.id.passwordVerif);
    }

    public void passwordChange(View view){
        String passwordFirst=ET_passwordFirst.getText().toString();
        String passwordConfirmation=ET_passwordVerif.getText().toString();

    }
}
