package com.example.pageWeb.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RiskRapport {

    private String id;

    private Risk risk;

    private List<String> terminologyList = new ArrayList<>();

    private Integer age;

    private String address;

    private Sexe sexe;

    private Integer phoneNumber;

    private String firstName;

    private String lastName;

    private Integer idPatientForRiskRapport;

    private List<Note> noteList = new ArrayList<>();
}
