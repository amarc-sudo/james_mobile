package com.iut.james_mobile.serviceApi;

import android.widget.Spinner;

import com.iut.james_mobile.apiobject.Etudiant;
import com.iut.james_mobile.apiobject.Formation;
import com.iut.james_mobile.apiobject.Matiere;
import com.iut.james_mobile.apiobject.Professeur;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ServiceAPI {

    private HttpPost httpPost;

    private HttpClient httpClient;

    private HttpResponse response;

    private String urlLocal="http://10.0.2.2:8080";

    private String urlProd="http://146.59.234.40:8080";

    public Professeur correctLoginAndPassword(String login,String password) throws JSONException, IOException {
        JSONObject jsonLogin=new JSONObject();
        jsonLogin.put("email",login);
        jsonLogin.put("password",password);
        StringEntity se=new StringEntity(jsonLogin.toString());
        this.prepareHttpPost("/rest/api/professeur/correctLogin",se);
        httpClient = new DefaultHttpClient(this.getHttpParams());
        response= httpClient.execute(httpPost);
        InputStream instream = response.getEntity().getContent();
        String result = convertStreamToString(instream);
        try{
            JSONObject jsonProfesseur = new JSONObject(result);
            Professeur professeur=new Professeur(jsonProfesseur);
            return professeur;
        }
        catch(JSONException | ParseException e){
            return null;
        }
    }

    public void createNewCours(Professeur professeur, Matiere matiere, String debutCours, String finCours, Map<Integer, Integer> map) throws JSONException, IOException {
        JSONObject jsonCours=new JSONObject();
        jsonCours.put("professeur",professeur.getIdProfesseur());
        jsonCours.put("matiere",matiere.getIdMatiere());
        jsonCours.put("debut",debutCours);
        jsonCours.put("fin",finCours);
        for (Iterator i=map.keySet().iterator();i.hasNext();){
            Integer idEtudiant= (Integer) i.next();
            jsonCours.put(Integer.toString(idEtudiant),map.get(idEtudiant));
        }

        System.out.println(jsonCours.toString());
        StringEntity se=new StringEntity(jsonCours.toString());
        System.out.println(se.toString());
        this.prepareHttpPost("/rest/api/cours/addCours",se);
        httpClient = new DefaultHttpClient(this.getHttpParams());
        response= httpClient.execute(httpPost);

    }

    public List<Etudiant> getEtudiantOfProfesseur(Professeur professeur) throws JSONException, IOException {
        System.out.println("On fait la demande");
        Set<Formation> formations=professeur.getFormations();
        JSONObject jsonFormation=new JSONObject();
        int compteur=0;
        for (Iterator<Formation> i = formations.iterator(); i.hasNext();){
            Formation formation=i.next();
            jsonFormation.put(Integer.toString(compteur),formation.getIdFormation());
            compteur++;
        }
        StringEntity se=new StringEntity(jsonFormation.toString());
        this.prepareHttpPost("/rest/api/etudiant/listEtudiant",se);
        httpClient = new DefaultHttpClient(this.getHttpParams());
        response= httpClient.execute(httpPost);
        InputStream instream = response.getEntity().getContent();
        String result = convertStreamToString(instream);
        try{
            JSONArray jsonEtudiants= new JSONArray(result);
            System.out.println(jsonEtudiants.toString());
            return getAllEtudiants(jsonEtudiants);
        }
        catch(JSONException | ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Matiere> getMatiereOfProfesseur(Professeur professeur) throws IOException, JSONException {
        Set<Formation> formations=professeur.getFormations();
        JSONObject jsonFormation=new JSONObject();
        int compteur=0;
        for (Iterator<Formation> i = formations.iterator(); i.hasNext();){
            Formation formation=i.next();
            jsonFormation.put(Integer.toString(compteur),formation.getIdFormation());
            compteur++;
        }
        StringEntity se=new StringEntity(jsonFormation.toString());
        this.prepareHttpPost("/rest/api/matiere/listMatiere",se);
        httpClient = new DefaultHttpClient(this.getHttpParams());
        response= httpClient.execute(httpPost);
        InputStream instream = response.getEntity().getContent();
        String result = convertStreamToString(instream);
        try{
            JSONArray jsonMatieres= new JSONArray(result);
            return getAllMatiere(jsonMatieres);
        }
        catch(JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public void sendSignature(Etudiant etudiant,String signature) throws JSONException, IOException {
        JSONObject jsonSignature=new JSONObject();
        jsonSignature.put("etudiant",etudiant.getIdEtudiant());
        jsonSignature.put("signature",signature);
        StringEntity se=new StringEntity(jsonSignature.toString());
        this.prepareHttpPost("/rest/api/etudiant/signature",se);
        httpClient = new DefaultHttpClient(this.getHttpParams());
        response= httpClient.execute(httpPost);
    }

    private List<Matiere> getAllMatiere(JSONArray jsonMatieres) throws JSONException {
        List<Matiere> matiereList=new ArrayList<>();
        for (int i=0;i<jsonMatieres.length();i++){
            matiereList.add(new Matiere(jsonMatieres.getJSONObject(i)));
        }
        return matiereList;
    }

    private List<Etudiant> getAllEtudiants(JSONArray jsonEtudiants) throws JSONException, ParseException {
        List<Etudiant> etudiantList=new ArrayList<>();
        for (int i=0;i<jsonEtudiants.length();i++){
            etudiantList.add(new Etudiant(jsonEtudiants.getJSONObject(i)));
        }
        return etudiantList;
    }



    public HttpParams getHttpParams(){
        HttpParams httpParameters = new BasicHttpParams();
        int timeoutConnection = 10000;
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        int timeoutSocket = 10000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        return httpParameters;
    }

    public void prepareHttpPost(String urlAPI,StringEntity se){
        httpPost=new HttpPost(urlLocal+urlAPI);
        httpPost.setEntity(se);
        httpPost.setHeader("Content-type","application/json");
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
