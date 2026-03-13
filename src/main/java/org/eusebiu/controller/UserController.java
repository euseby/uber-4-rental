package org.eusebiu.controller;

import org.eusebiu.dto.LoginRequest;
import org.eusebiu.models.User;
import org.eusebiu.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
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
    public User login(@RequestBody LoginRequest loginRequest) {
        return userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
    }
}
