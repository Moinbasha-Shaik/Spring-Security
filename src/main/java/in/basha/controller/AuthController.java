package in.basha.controller;


import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.basha.dto.AuthRequest;
import in.basha.dto.AuthResponse;
import in.basha.dto.SignupRequest;
import in.basha.entity.Role;
import in.basha.entity.User;
import in.basha.repo.RoleRepository;
import in.basha.repo.UserRepository;
import in.basha.service.CustomUserDetailsService;
import in.basha.service.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;



    
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtService.generateToken(userDetails);

        
        User user = userRepo.findByUsername(request.getUsername()).orElseThrow(); // if you have a JPA repo

        return new AuthResponse(token, user.getUsername(), user.getEmail());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if (userRepo.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        for (String roleName : strRoles) {
            Role role = roleRepo.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Error: Role not found -> " + roleName));
            roles.add(role);
        }

        user.setRoles(roles);
        userRepo.save(user);


        return ResponseEntity.ok(Map.of("message", "User registered successfully!"));

    }
}

