package com.iut.james_mobile.apiobject;

import java.util.Date;


public class Personne {

    private int idPersonne;

    private String nom;

    private String prenom;

    private Date dateCreation;

    public Personne(int idPersonne, String nom, String prenom, Date dateCreation){
        this.idPersonne=idPersonne;
        this.nom=nom;
        this.prenom=prenom;
        this.dateCreation=dateCreation;
    }

    public String toString(){
        return "Je suis "+nom+" "+prenom+" qui a pour id de personne"+idPersonne+"\n"
        +"J'ai ete crée le "+dateCreation.toString()+"\n";

    }

}
