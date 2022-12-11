package com.example.pageWeb.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
public class Note {

    private Integer idNote;

    private Date dateNote;

    private String takeNote;

    private Integer idPatientForNote;

}
