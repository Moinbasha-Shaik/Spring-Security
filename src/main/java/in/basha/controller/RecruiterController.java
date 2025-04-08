package in.basha.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruiter")
public class RecruiterController {

    @GetMapping("/home")
    @PreAuthorize("hasRole('ROLE_RECRUITER')")
    public String recruiterPanel() {
        return "Welcome Recruiter";
    }
}

