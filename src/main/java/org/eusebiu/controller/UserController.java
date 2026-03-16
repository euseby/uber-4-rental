package org.eusebiu.controller;

import org.eusebiu.dto.LoginRequest;
import org.eusebiu.dto.LoginResponse; // Importam noul pachet de livrare
import org.eusebiu.models.User;
// ATENTIE: Aici pui importul corect catre clasa JwtUtil pe care ai creat-o (ex: org.eusebiu.security.JwtUtil)
import org.eusebiu.security.JwtUtil;
import org.eusebiu.service.UserService;
import org.springframework.http.ResponseEntity; // Folosim asta ca sa putem returna si Erori daca greseste parola
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil; // 1. Am adus Fabrica de Bratari!

    // 2. Am adaugat jwtUtil in constructor ca sa-l injecteze Spring Boot
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    //API 1 REGISTER
    //LINK: POST http://localhost:8080/api/users/register
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    //API 2 LOGIN
    //Link: POST http://localhost:8080/api/users/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Pasul A: Verificam userul si parola in Service (ca si pana acum)
            User user = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());

            // Pasul B: Parola e corecta! Hai sa printam Token-ul (Bratara)
            String token = jwtUtil.generateToken(user.getEmail());

            // Pasul C: Impachetam Token-ul, Username-ul si Email-ul in cutia noastra speciala
            LoginResponse response = new LoginResponse(token, user.getUsername(), user.getEmail());

            // Trimitem pachetul (Status 200 OK)
            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            // Daca a aruncat eroare de parola sau email gresit din UserService, dam Status 400 Bad Request
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}