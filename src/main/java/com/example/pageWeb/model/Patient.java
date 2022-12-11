package com.example.pageWeb.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Patient {

    private Integer idPatient;

    private String firstName;

    private String lastName;

    private Integer age = 1;

    private String address;

    private Sexe sexe;

    private Integer phoneNumber;

    private Risk risk = Risk.NONE;

    private List<String> terminologyList = new ArrayList<>();

    private List<Note> noteList = new ArrayList<>();

}
