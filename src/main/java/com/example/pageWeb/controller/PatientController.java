package com.example.pageWeb.controller;


import com.example.pageWeb.model.Patient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Configuration
@Controller
public class PatientController {

    @Value("${server.patient}")
    private String serverPatient;
    @Value("${server.note}")
    private String serverNote;

    @GetMapping("/patient/allPatientList")
    public String patientList(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        Patient[] allPatientList = restTemplate.getForObject( serverPatient + "/patient/allPatientList", Patient[].class);
        List<Patient> allPatientListFinal = Arrays.asList(allPatientList);
        model.addAttribute("allPatient", allPatientListFinal);
        return "patient/allPatientList";
    }


    @GetMapping("/patient/addPatient")
    public String addPatientForm(Patient patient) {
        return "patient/addPatient";
    }

    @RequestMapping(value = "/patient/validate", method = { RequestMethod.GET, RequestMethod.POST })
    public String validateAddPatient(Patient patient, Model model) {
        model.addAttribute("patient", patient);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(serverPatient +  "/patient/validate", patient);
        return "redirect:/patient/allPatientList";
    }

    @GetMapping("/patient/updatePatient/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Patient patient, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        patient = restTemplate.getForObject( serverPatient + "/patient/getOnePatient/"+ id, Patient.class);
        model.addAttribute("updatePatient", patient);
        return "patient/updatePatient";
    }

    @PostMapping("/patient/updatePatient/{id}")
    public String updatePatient(@PathVariable("id") Integer id, Patient patient) {
        System.out.println("New patient : "+patient);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(serverPatient + "/patient/updatePatient/" + id, patient);
        return "redirect:/patient/allPatientList";
    }

    @RequestMapping(value = "/patient/delete/{id}", method = {RequestMethod.POST, RequestMethod.GET})
    public String showDeletePatient(@PathVariable("id") Integer id, Patient patient) {
        RestTemplate restTemplate = new RestTemplate();
        patient = restTemplate.getForObject(serverPatient + "/patient/getOnePatient/"+ id, Patient.class);
        restTemplate.put(serverPatient + "/patient/delete/"+ id, patient.getIdPatient());
        return "redirect:/patient/allPatientList";
    }

            /*
@GetMapping("/patient/updatePatient/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("updatePatient", patientService.findByPatientId(id));
        return "patient/updatePatient";
    }

    @PostMapping("/patient/updatePatient/{id}")
    public String updatePatient(@PathVariable("id") Integer id, Patient patient, BindingResult result, Model model) {
        if(result.hasErrors()){
            return "patient/updatePatient/{id}";
        }else{
            patient.setIdPatient(id);
            patientService.addNewPatient(patient);
            model.addAttribute("allPatient", patientService.findAllPatient());
        }
        return "patient/allPatientList";
    }

    @GetMapping("/patient/delete/{id}")
    public String showDeletePatient(@PathVariable("id") Integer id, Model model) {
        Patient patient = patientService.findByPatientId(id);
        patientService.deletePatient(patientService.findByPatientId(id));
        model.addAttribute("allPatient", patientService.findAllPatient());
        return "patient/allPatientList";
    }
     */


}
