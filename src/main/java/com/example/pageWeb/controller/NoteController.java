package com.example.pageWeb.controller;

import com.example.pageWeb.model.Note;
import com.example.pageWeb.model.Patient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Controller
@Configuration
public class NoteController {

    @Value("${server.patient}")
    private String serverPatient;
    @Value("${server.note}")
    private String serverNote;

    /**
     * Obtenir toute les notes d'un patients
     * @return
     */
    @GetMapping("/note/notePatient/{idPatient}")
    public String noteList(@PathVariable("idPatient") Integer idPatient, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        Patient patient = restTemplate.getForObject(serverPatient + "/patient/getOnePatient/" + idPatient, Patient.class);
        Note[] noteList = restTemplate.getForObject(serverNote + "/note/notePatient/" + idPatient, Note[].class);

        List<Note> allPatientListFinal = Arrays.asList(noteList);
        model.addAttribute("allNote", allPatientListFinal);
        model.addAttribute("patient", patient);
        return "note/notePatient";
    }

    /**
    * Afficher formuler d'ajout d'une note au patient
    * @param
    * @return
    */
    @GetMapping("/note/addNewNotePatient/{idPatient}")
    public String addNotePatientForm(@PathVariable("idPatient") Integer idPatient, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        Patient patient = restTemplate.getForObject(serverPatient + "/patient/getOnePatient/" + idPatient, Patient.class);
        Note note = new Note();
        model.addAttribute("note", note);
        model.addAttribute("patient", patient);
        return "note/addNewNotePatient";
    }

    /**
    * Formulaire de validation de création de la note au patient
    * @param
    * @return
    */
    @RequestMapping(value = "/note/addNewNotePatientValidate/{idPatient}", method = { RequestMethod.GET, RequestMethod.POST })
    public String validateAddNotePatient(@PathVariable("idPatient") Integer idPatient, Note note) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(serverNote + "/note/validate/" + idPatient, note);
        return "redirect:/note/notePatient/{idPatient}";
    }

    /**
     * Afficher le formulaire pour mettre à jour une note
     * @param idNote
     * @param model
     * @return
     */
    @GetMapping("/note/updateNote/{idNote}")
    public String showUpdateNoteForm(@PathVariable("idNote") Integer idNote, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        Note note = restTemplate.getForObject(serverNote + "/note/getOneNoteById/" +idNote, Note.class);
        Patient patient = restTemplate.getForObject(serverPatient + "/patient/getOnePatient/" + note.getIdPatientForNote(), Patient.class);
        model.addAttribute("patient", patient);
        model.addAttribute("updateNote", note);
        return "note/updateNote";
    }

    /**
     * Valide la mise à jour de la nouvelle note
     * @param idNote
     * @param note
     * @return
     */
    @RequestMapping(value = "/note/updateNoteValidate/{idNote}", method = { RequestMethod.GET, RequestMethod.POST })
    public String updateNoteValidate(@PathVariable("idNote") Integer idNote, Note note) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(serverNote + "/note/updateNote/" + idNote, note);
        Note noteForRedirect = restTemplate.getForObject(serverNote + "/note/getOneNoteById/" +idNote, Note.class);
        Patient patient = restTemplate.getForObject(serverPatient + "/patient/getOnePatient/" + noteForRedirect.getIdPatientForNote(), Patient.class);
        return "redirect:/note/notePatient/" + patient.getIdPatient();
    }

    /**
     * Supprimer une note de la base de données
     * @param idNote
     * @return
     */
    @RequestMapping(value = "/note/delete/{idNote}", method = {RequestMethod.POST, RequestMethod.GET})
    public String showDeleteNotePAtient(@PathVariable("idNote") Integer idNote) {
        RestTemplate restTemplate = new RestTemplate();
        Note noteForRedirect = restTemplate.getForObject(serverNote + "/note/getOneNoteById/" +idNote, Note.class);
        Patient patient = restTemplate.getForObject(serverPatient + "/patient/getOnePatient/" + noteForRedirect.getIdPatientForNote(), Patient.class);
        restTemplate.put(serverNote + "/note/delete/" + idNote, idNote);
        return "redirect:/note/notePatient/" + patient.getIdPatient();
    }

}
