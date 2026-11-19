package com.sip.vcm.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ProprietaireDTO {

    private Long id;

    @NotBlank
    private String nom;

    @NotBlank
    @Pattern(regexp = "^[+]?[0-9\\s\\-]{8,20}$", message = "Format de téléphone invalide")
    private String telephone;

    @NotBlank
    @Email(message = "Format d'email invalide")
    private String email;

    private String adresse;
    private String photo;
    private String activite;
}
