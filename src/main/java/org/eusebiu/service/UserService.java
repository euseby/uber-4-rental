package org.eusebiu.service;

import org.eusebiu.models.User;
import org.eusebiu.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User registerUser(User user){
        //verific daca exista emailul
        User userExistent = userRepository.findByEmail(user.getEmail());
        if (userExistent != null) {
            throw new RuntimeException("Acest email este deja folosit!");
        }
        return userRepository.save(user);
    }
    public User loginUser(String email, String password){
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new RuntimeException("Nu exista niciun cont cu acest email!");
        }
        if(!user.getPassword().equals(password)){
            throw new RuntimeException("Parola incorecta!");
        }
        return user;
    }
}
