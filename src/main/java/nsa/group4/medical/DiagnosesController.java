package nsa.group4.medical;

import nsa.group4.medical.data.DiagnosisRepositoryJPA;
import nsa.group4.medical.domains.Diagnosis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class DiagnosesController {

    private DiagnosisRepositoryJPA diagnosisRepositoryJPA;

    @Autowired
    public DiagnosesController(DiagnosisRepositoryJPA diagnosisRepositoryJPA) {
        this.diagnosisRepositoryJPA = diagnosisRepositoryJPA;
    }

    @GetMapping("/{index}")
    public String getDiagnoses(Model model, @PathVariable final Long index) {
        List<Diagnosis> diagnoses = diagnosisRepositoryJPA.findByCategoryId(index);
        model.addAttribute("diagnoses", diagnoses);
        return "home";
    }
}
