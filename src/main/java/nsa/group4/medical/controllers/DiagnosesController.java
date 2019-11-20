package nsa.group4.medical.controllers;

import nsa.group4.medical.data.DiagnosisRepositoryJPA;
import nsa.group4.medical.domains.Diagnosis;
import nsa.group4.medical.web.CaseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class DiagnosesController {

    private DiagnosisRepositoryJPA diagnosisRepositoryJPA;

    @Autowired
    public DiagnosesController(DiagnosisRepositoryJPA diagnosisRepositoryJPA) {
        this.diagnosisRepositoryJPA = diagnosisRepositoryJPA;
    }

    @GetMapping("/{index}")
    public String getDiagnoses(Model model,
                               @PathVariable final Long index) {
        List<Diagnosis> diagnoses = diagnosisRepositoryJPA.findByCategoryIdOrderByNameAsc(index);
        model.addAttribute("diagnosisKey", new Diagnosis());
        model.addAttribute("diagnoses", diagnoses);
        return "home";
    }

    @PostMapping("/{index}")
    public String newDiagnosis(@PathVariable final Long index,
                               @ModelAttribute("diagnosisKey") Diagnosis diagnosis,
                               Model model) {
        diagnosis.setCategoryId(index);
        diagnosisRepositoryJPA.save(diagnosis);
        List<Diagnosis> diagnoses = diagnosisRepositoryJPA.findByCategoryIdOrderByNameAsc(index);
        model.addAttribute("diagnosisKey", new Diagnosis());
        model.addAttribute("diagnoses", diagnoses);
        return "home";

    }
}
