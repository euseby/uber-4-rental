package org.eusebiu.service;

import lombok.AllArgsConstructor;
import org.eusebiu.dto.UpdateProfileRequest;
import org.eusebiu.models.User;
import org.eusebiu.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private EmailService emailService;
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
        
        // Seteaza statusul de verificare email
        user.setEmailVerified(false);
        user.setVerificationToken(java.util.UUID.randomUUID().toString());
        
        User savedUser = userRepository.save(user);
        
        // Trimite emailul in background (fara sa blocheze)
        new Thread(() -> emailService.sendVerificationEmail(savedUser.getEmail(), savedUser.getVerificationToken())).start();
        
        return savedUser;
    }
    public User loginUser(String email, String password){
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new RuntimeException("Nu exista niciun cont cu acest email!");
        }
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException("Parola incorecta!");
        }
        if (Boolean.FALSE.equals(user.getEmailVerified())) {
            throw new RuntimeException("Contul nu este verificat! Te rugam sa accesezi linkul primit pe email.");
        }
        return user;
    }
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("Utilizatorul nu a fost gasit!");
        }
        return user;
    }
    public User updateUserProfile(String email, UpdateProfileRequest cerereUpdate) {
        // 1. Gasim user-ul curent in baza de date
        User user = getUserByEmail(email);

        // 2. Ii actualizam datele cu ce a venit de pe frontend
        user.setFirstName(cerereUpdate.getFirstName());
        user.setLastName(cerereUpdate.getLastName());
        user.setPhone(cerereUpdate.getPhone());
        user.setAddress(cerereUpdate.getAddress());
        user.setLicenseNumber(cerereUpdate.getLicenseNumber());
        user.setLicenseExpiry(cerereUpdate.getLicenseExpiry());
        user.setBio(cerereUpdate.getBio());

        // 3. Salvam in baza de date noul user modificat
        return userRepository.save(user);
    }

    public void verifyUser(String token) {
        User user = userRepository.findByVerificationToken(token);
        if (user == null) {
            throw new RuntimeException("Token invalid sau expirat.");
        }
        user.setEmailVerified(true);
        user.setVerificationToken(null);
        userRepository.save(user);
    }
}
