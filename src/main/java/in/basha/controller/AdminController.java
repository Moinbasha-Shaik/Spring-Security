package in.basha.controller;



import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/home")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminDashboard() {
        return "Welcome Admin";
    }
}

