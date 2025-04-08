package in.basha.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applicant")
public class ApplicantController {

    @GetMapping("/home")
    @PreAuthorize("hasRole('ROLE_APPLICANT')")
    public String applicantHome() {
        return "Welcome Applicant";
    }
}

