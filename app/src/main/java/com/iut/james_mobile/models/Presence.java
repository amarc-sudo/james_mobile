package com.iut.james_mobile.models;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Presence implements Serializable {

    private int idPresence;

    private Etudiant etudiant;

    private Cours cours;

    private TableData etatPresence;

    public Presence(Etudiant etudiant,
                    Cours cours
                    ) {
        this.etudiant = etudiant;
        this.cours = cours;
        this.etatPresence = new TableData(convertPositionSpinnerToCode(etudiant.getPositionSpinner()));
    }

    private static String convertPositionSpinnerToCode(int positionSpinner) {
        switch (positionSpinner) {
            case 1:
                return "ret";
            case 2:
                return "abs";
            default:
                return "pre";
        }
    }

    @Override
    public String toString() {
        return "Presence{" +
                "idPresence=" + idPresence +
                ", etudiant=" + etudiant +
                ", cours=" + cours +
                ", etatPresence=" + etatPresence +
                "} \n";
    }
}
