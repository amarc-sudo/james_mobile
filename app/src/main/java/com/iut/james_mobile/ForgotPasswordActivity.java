package com.iut.james_mobile;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.iut.james_mobile.serviceApi.ServiceForgotPassword;
import org.json.JSONException;
import java.io.IOException;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Button BT_emailButton;

    private EditText emailForgotPassword;

    private  String emailForgotPasswordString ;

    private ServiceForgotPassword serviceForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        BT_emailButton=(Button) findViewById(R.id.BT_emailButton);
        emailForgotPassword=(EditText) findViewById(R.id.emailForgotPassword);
        serviceForgotPassword=new ServiceForgotPassword();
    }

    public void getAndSendEmail(View view) throws IOException, JSONException {
       emailForgotPasswordString = emailForgotPassword.getText().toString();
        if(emailForgotPasswordString.isEmpty()) {
            Toast.makeText(this, "Champs vide, veuillez entrer une adresse email valide", Toast.LENGTH_LONG).show();
        }
        else{
            boolean email = serviceForgotPassword.isEmailCorrect(emailForgotPasswordString);
            if(email==true) {
                Toast.makeText(this, "Un email vient d'être envoyé sur votre adresse email", Toast.LENGTH_LONG).show();
                this.finish();
            }
            else{
                Toast.makeText(this, "Veuillez entrer une adresse email valide", Toast.LENGTH_LONG).show();
            }
        }
    }

}
