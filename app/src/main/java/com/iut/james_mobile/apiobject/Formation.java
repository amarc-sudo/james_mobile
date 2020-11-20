package com.iut.james_mobile.apiobject;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class Formation implements Serializable {

    private int idFormation;

    private String intitule;

    public Formation(int idFormation,String intitule){
        this.idFormation=idFormation;
        this.intitule=intitule;
    }


    @Override
    public String toString() {
        return "Je suis prof en "+this.intitule+"\n";
    }
}
