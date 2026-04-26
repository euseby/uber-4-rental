package org.eusebiu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String toEmail, String token) {
        String verificationUrl = "http://localhost:8080/api/users/verify?token=" + token;
        String subject = "Ride4Rent - Verifica adresa de email";
        String body = "Salut!\n\nTe rugam sa dai click pe linkul de mai jos pentru a-ti activa contul:\n" + verificationUrl + "\n\nEchipa Ride4Rent";

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("ride4rent@example.com");
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
            System.out.println("Email de verificare trimis catre " + toEmail);
        } catch (Exception e) {
            System.out.println("-----------------------------------------------------");
            System.out.println("ATENTIE: Nu ai configurat credentialele de email reale in application.properties!");
            System.out.println("Din acest motiv, emailul NU a putut fi trimis.");
            System.out.println("Poti aproba contul manual facand click pe acest link (doar pt Development):");
            System.out.println(verificationUrl);
            System.out.println("-----------------------------------------------------");
        }
    }
}
