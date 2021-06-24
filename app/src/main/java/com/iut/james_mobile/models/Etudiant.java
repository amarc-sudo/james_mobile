package com.iut.james_mobile.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class Etudiant implements Serializable {

    private int idEtudiant;

    private Personne personne;

    private boolean hasSigned;

    private Formation formation;

    private Integer groupe;

    private Integer nombreRetards;

    private Integer nombreAbsences;

    private String signature;

    private String adresseMail;

    @JsonIgnore
    private int positionSpinner;

    public Etudiant(@JsonProperty("idEtudiant") int idEtudiant,
                    @JsonProperty("personne") Personne personne,
                    @JsonProperty("hasSigned") boolean hasSigned,
                    @JsonProperty("formation") Formation formation,
                    @JsonProperty("adresseMail") String adresseMail,
                    @JsonProperty("groupe") Integer groupe,
                    @JsonProperty("nombreRetards") Integer nombreRetards,
                    @JsonProperty("nombreAbsences") Integer nombreAbsences) {
        this.idEtudiant = idEtudiant;
        this.personne = personne;
        this.hasSigned = hasSigned;
        this.formation = formation;
        this.groupe = groupe;
        this.adresseMail = adresseMail;
        this.nombreAbsences = nombreAbsences;
        this.nombreRetards = nombreRetards;
        this.positionSpinner = 0;
    }

}
