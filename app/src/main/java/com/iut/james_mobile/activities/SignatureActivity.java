package com.iut.james_mobile.activities;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.iut.james_mobile.R;
import com.iut.james_mobile.models.Etudiant;
import com.iut.james_mobile.models.Professeur;
import com.iut.james_mobile.services.ServiceEtudiant;
import com.iut.james_mobile.services.ServiceProfesseur;

import com.iut.james_mobile.views.PaintView;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class SignatureActivity extends AppCompatActivity {

    private TextView TV_nomEleve;

    private PaintView paintView;

    private Etudiant etudiant;

    private Professeur professeur;

    private String formationSelectionne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        etudiant = (Etudiant) intent.getSerializableExtra("etudiant");
        professeur = (Professeur) intent.getSerializableExtra("professeur");
        setContentView(R.layout.activity_signature);
        TV_nomEleve = findViewById(R.id.textView_nomSignature);
        if (etudiant != null) {
            TV_nomEleve.setText(etudiant.getPersonne().getNom() + " " + etudiant.getPersonne().getPrenom());
        } else {
            TV_nomEleve.setText(professeur.getPersonne().getNom() + " " + professeur.getPersonne().getPrenom());
        }
        paintView = findViewById(R.id.paintView);
        formationSelectionne = (String) intent.getSerializableExtra("formation");
    }

    public void confirmationSignature(View view) throws IOException, JSONException {
        paintView.buildDrawingCache(true);
        Bitmap returnedBitmap = Bitmap.createBitmap(paintView.getWidth(), paintView.getHeight(), Bitmap.Config.ARGB_8888);
        paintView.setDrawingCacheEnabled(false);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = paintView.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        paintView.draw(canvas);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        returnedBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.signatureConfirmation))
                .setMessage(getResources().getString(R.string.alertSignatureConfirmation))
                .setPositiveButton(getResources().getString(R.string.oui), (dialogInterface, i) -> {
                    if (etudiant == null) {
                        professeur.setSignature(encoded);
                        try {
                            professeur = new ServiceProfesseur().update(professeur);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        etudiant.setSignature(encoded);
                        try {
                            etudiant = new ServiceEtudiant().update(etudiant);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.signatureEnvoyee), Toast.LENGTH_LONG).show();
                    Go();
                })
                .setNegativeButton(R.string.non, (dialogInterface, i) -> Toast.makeText(getApplicationContext(), getResources().getString(R.string.annulationEnvoiSignature), Toast.LENGTH_LONG).show())
                .show();


    }

    public void Go() {
        Intent intent;
        if (etudiant != null) {
            intent = new Intent(this, AppelActivity.class);
            intent.putExtra("idModifie", this.etudiant.getIdEtudiant());
            intent.putExtra("professeur", professeur);
            intent.putExtra("formation", formationSelectionne);
        } else {
            if (getIntent().getIntExtra("modificationSignature", 0) == 1) {
                intent = new Intent(this, ParametreActivity.class);
                professeur.setHasSigned(true);
                intent.putExtra("professeur", professeur);
                intent.putExtra("modificationSignature", 0);
            } else {
                intent = new Intent(this, WelcomeActivity.class);
                professeur.setHasSigned(true);
                intent.putExtra("professeur", professeur);
            }
        }
        this.finish();
        startActivity(intent);

    }

    public void reset(View view) {
        paintView.resetSignature();
    }

}
