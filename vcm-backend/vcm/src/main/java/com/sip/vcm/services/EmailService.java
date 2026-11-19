package com.sip.vcm.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendNewUserNotification(String userLogin, String userNom, String userPrenom) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(adminEmail);
            message.setSubject("[VetCare Manager] Nouveau compte créé");
            message.setText(String.format(
                    "Un nouveau compte a été créé sur VetCare Manager.\n\n" +
                    "Login : %s\n" +
                    "Nom : %s %s\n\n" +
                    "Veuillez vérifier et attribuer les rôles si nécessaire.",
                    userLogin, userPrenom, userNom
            ));
            mailSender.send(message);
        } catch (Exception e) {
            // Log the error but don't fail the registration
            System.err.println("Échec d'envoi de l'email de notification : " + e.getMessage());
        }
    }
}
