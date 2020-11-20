package com.iut.james_mobile.apiobject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contact {
    private int idContact;

    private String adresseMail;


    public Contact(int idContact, String adresseMail) {
        this.idContact=idContact;
        this.adresseMail=adresseMail;
    }

    public String toString(){
        return "Mon id de contact est: "+idContact+"\n"
                +"Mon adresse mail est : "+adresseMail+"\n";
    }
}
