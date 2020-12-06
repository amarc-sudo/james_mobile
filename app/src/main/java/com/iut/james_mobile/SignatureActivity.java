package com.iut.james_mobile;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.iut.james_mobile.dessiner.PaintView;


public class SignatureActivity extends AppCompatActivity {
    private Button BT_signature_confirmation ;
    private Button BT_resetSignature;
    private TextView TV_nomEleve;
    private PaintView paintView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        TV_nomEleve=findViewById(R.id.textView_nomSignature);
        paintView=findViewById(R.id.paintView);
        System.out.println("Je suis après le paintview");
    }//onCreate

    public void confirmationSignature(View view){
        Toast.makeText(this, "Signature envoyée ! ", Toast.LENGTH_LONG).show();
    }

   /* public void setPrenomTV(Etudiant etudiant){
        TV_nomEleve.setText( "Vous êtes : " + Etudiant.getNom() + Etudiant.getPrenom());
    }//setPrenomTV
    */

    public void reset(View view) {
        //paintView.invalidate();
        paintView.resetSignature();
        System.out.println("Je suis dans l'activité");
    }





}
