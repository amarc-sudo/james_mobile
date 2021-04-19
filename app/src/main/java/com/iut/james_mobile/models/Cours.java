package com.iut.james_mobile.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(value = { "timestamp", "status", "error", "trace"})
public class Cours implements Serializable {

    private int idCours;

    private Matiere matiere;

    private Date date;

    private Time begin;

    private Time end;

    private Professeur professeur;

    private TableData etat;

    /**
     * Constructeur pour un nouveau cours qui va
     * être envoyé à l'API
     *
     * @param matiere
     * @param begin
     * @param end
     * @param professeur
     */
    public Cours(Matiere matiere,
                 Time begin,
                 Time end,
                 Professeur professeur) {
        this.matiere = matiere;
        this.begin = begin;
        this.end = end;
        this.professeur = professeur;
        this.etat = new TableData("non_env");
    }

    public Cours(@JsonProperty("idCours") int idCours) {
        this.idCours = idCours;
    }

    @Override
    public String toString() {
        return "Cours{" +
                "idCours=" + idCours +
                ", matiere=" + matiere +
                ", date=" + date +
                ", begin=" + begin +
                ", end=" + end +
                ", etat=" + etat +
                '}';
    }
}
