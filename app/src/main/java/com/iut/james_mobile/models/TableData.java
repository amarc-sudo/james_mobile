package com.iut.james_mobile.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableData implements Serializable {

    private int idData;

    private String donnees;

    private String code;

    public TableData(String code) {
        this.code = code;
    }

    public TableData(@JsonProperty("idData") int idData,
                     @JsonProperty("donnees") String donnees,
                     @JsonProperty("code") String code) {
        this.idData = idData;
        this.donnees = donnees;
        this.code = code;
    }

}
