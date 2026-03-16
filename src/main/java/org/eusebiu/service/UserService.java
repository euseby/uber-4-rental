package org.eusebiu.service;

import org.eusebiu.models.User;
import org.eusebiu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    // Aducem masina de criptat parole!
    @Autowired
    private PasswordEncoder passwordEncoder;
    public User registerUser(User user){
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new RuntimeException("Numele este obligatoriu!");
        }
        //verific daca exista emailul
        User userExistent = userRepository.findByEmail(user.getEmail());
        if (userExistent != null) {
            throw new RuntimeException("Acest email este deja folosit!");
        }
        String parolaCriptata = passwordEncoder.encode(user.getPassword());
        user.setPassword(parolaCriptata);
        return userRepository.save(user);
    }
    public User loginUser(String email, String password){
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new RuntimeException("Nu exista niciun cont cu acest email!");
        }
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException("Parola incorecta!");
        }
        return user;
    }
}
