package com.sip.vcm.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank
    @Email
    private String login;

    @NotBlank
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String password;

    @NotBlank
    private String nom;

    @NotBlank
    private String prenom;

    private String tel;

    // Optional: role name, defaults to COLLABORATEUR
    private String role;
}
