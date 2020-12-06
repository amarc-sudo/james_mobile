package com.iut.james_mobile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.iut.james_mobile.apiobject.Etudiant;
import com.iut.james_mobile.apiobject.Professeur;
import com.iut.james_mobile.dessiner.PaintView;
import com.iut.james_mobile.serviceApi.ServiceAPI;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import lombok.SneakyThrows;


public class SignatureActivity extends AppCompatActivity {
    private Button BT_signature_confirmation ;
    private Button BT_resetSignature;
    private TextView TV_nomEleve;
    private PaintView paintView;
    private Etudiant etudiant;
    private Professeur professeur;
    private String formationSelectionne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        etudiant= (Etudiant) intent.getSerializableExtra("etudiant");
        professeur=(Professeur)intent.getSerializableExtra("professeur");
        setContentView(R.layout.activity_signature);
        TV_nomEleve=findViewById(R.id.textView_nomSignature);
        TV_nomEleve.setText(etudiant.getPersonne().getNom()+" "+etudiant.getPersonne().getPrenom());
        paintView=findViewById(R.id.paintView);
        formationSelectionne=(String)intent.getSerializableExtra("formation");
    }//onCreate

    public void confirmationSignature(View view) throws IOException, JSONException {

        paintView.buildDrawingCache(true);
        Bitmap returnedBitmap = Bitmap.createBitmap(paintView.getWidth(), paintView.getHeight(),Bitmap.Config.ARGB_8888);
        paintView.setDrawingCacheEnabled(false);
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =paintView.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        paintView.draw(canvas);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        returnedBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        new AlertDialog.Builder(this)
                .setTitle("Confirmation de la signature")
                .setMessage("Voulez vous confirmer cette signature ?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @SneakyThrows
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ServiceAPI serviceAPI=new ServiceAPI();
                        serviceAPI.sendSignature(etudiant,encoded);
                        Toast.makeText(getApplicationContext(),"Signature envoyée !",Toast.LENGTH_LONG).show();
                        Go();
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"Annulation de l'ajout de la signature",Toast.LENGTH_LONG).show();
                    }
                })
                .show();



    }

    public void Go(){
        Intent intent=new Intent(this,AppelActivity.class);
        intent.putExtra("idModifie", this.etudiant.getIdEtudiant());
        intent.putExtra("professeur",professeur);
        intent.putExtra("formation",formationSelectionne);
        startActivity(intent);

    }



    public void reset(View view) {
        //paintView.invalidate();
        paintView.resetSignature();
    }





}
