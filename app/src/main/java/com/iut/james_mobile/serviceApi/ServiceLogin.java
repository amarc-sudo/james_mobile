package com.iut.james_mobile.serviceApi;

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
import java.util.Set;

public class ServiceLogin {

    private HttpPost httpPost;

    private HttpClient httpClient;

    private HttpResponse response;

    private String urlLocal="http://10.0.2.2:8080";

    private String urlProd="http://146.59.234.40:8080";

    public Professeur correctLoginAndPassword(String login,String password) throws JSONException, IOException {
        JSONObject jsonLogin=new JSONObject();
        jsonLogin.put("email",login);
        jsonLogin.put("password",password);
        httpPost=new HttpPost(urlLocal+"/rest/api/professeur/correctLogin");
        StringEntity se=new StringEntity(jsonLogin.toString());
        httpPost.setEntity(se);
        httpPost.setHeader("Content-type","application/json");
        System.out.println(jsonLogin.toString());

        HttpParams httpParameters = new BasicHttpParams();
        int timeoutConnection = 10000;
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        int timeoutSocket = 10000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

        httpClient = new DefaultHttpClient(httpParameters);
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

    public List<Etudiant> getEtudiantOfProfesseur(Professeur professeur) throws JSONException, IOException {
        Set<Formation> formations=professeur.getFormations();
        JSONObject jsonFormation=new JSONObject();
        int compteur=0;
        for (Iterator<Formation> i = formations.iterator(); i.hasNext();){
            Formation formation=i.next();
            jsonFormation.put(Integer.toString(compteur),formation.getIdFormation());
        }
        httpPost=new HttpPost(urlLocal+"/rest/api/etudiant/listEtudiant");
        StringEntity se=new StringEntity(jsonFormation.toString());
        httpPost.setEntity(se);
        httpPost.setHeader("Content-type","application/json");
        System.out.println(jsonFormation.toString());
        HttpParams httpParameters = new BasicHttpParams();
        int timeoutConnection = 10000;
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        int timeoutSocket = 10000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        httpClient = new DefaultHttpClient(httpParameters);
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
        }
        httpPost=new HttpPost(urlLocal+"/rest/api/matiere/listMatiere");
        StringEntity se=new StringEntity(jsonFormation.toString());
        httpPost.setEntity(se);
        httpPost.setHeader("Content-type","application/json");
        System.out.println(jsonFormation.toString());
        HttpParams httpParameters = new BasicHttpParams();
        int timeoutConnection = 10000;
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        int timeoutSocket = 10000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        httpClient = new DefaultHttpClient(httpParameters);
        response= httpClient.execute(httpPost);
        InputStream instream = response.getEntity().getContent();
        String result = convertStreamToString(instream);
        try{
            JSONArray jsonMatieres= new JSONArray(result);
            System.out.println(jsonMatieres.toString());
            return getAllMatiere(jsonMatieres);
        }
        catch(JSONException e){
            e.printStackTrace();
        }

        return null;
    }

    private List<Matiere> getAllMatiere(JSONArray jsonMatieres) throws JSONException {
        List<Matiere> matiereList=new ArrayList<>();
        for (int i=0;i<jsonMatieres.length();i++){
            matiereList.add(new Matiere(jsonMatieres.getJSONObject(i)));
        }
        System.out.println(matiereList.toString());
        return matiereList;
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

    private List<Etudiant> getAllEtudiants(JSONArray jsonEtudiants) throws JSONException, ParseException {
        List<Etudiant> etudiantList=new ArrayList<>();
        for (int i=0;i<jsonEtudiants.length();i++){
            etudiantList.add(new Etudiant(jsonEtudiants.getJSONObject(i)));
        }
        System.out.println(etudiantList.toString());
        return etudiantList;
    }
}
