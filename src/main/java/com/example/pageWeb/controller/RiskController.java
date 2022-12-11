package com.example.pageWeb.controller;

import com.example.pageWeb.model.RiskRapport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

@Configuration
@Controller
public class RiskController {

    @Value("${server.risk}")
    private String serverRisk;

    @RequestMapping(value = "risk/riskPatient/{idPatient}", method = {RequestMethod.GET, RequestMethod.POST})
    public String generateRapport(@PathVariable("idPatient") Integer idPatient, Model model){
        RestTemplate restTemplate = new RestTemplate();
        RiskRapport riskRapport = restTemplate.getForObject(serverRisk + "/risk/generateRiskRapport/"+idPatient, RiskRapport.class);
        model.addAttribute("riskRapport", riskRapport);
        return "risk/riskPatient";
    }
}
