package com.sip.vcm.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "proprietaires")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Proprietaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String nom;

    @NotBlank
    @Pattern(regexp = "^[+]?[0-9\\s\\-]{8,20}$", message = "Format de téléphone invalide")
    @Column(nullable = false, length = 20)
    private String telephone;

    @NotBlank
    @Email(message = "Format d'email invalide")
    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 255)
    private String adresse;

    @Column(length = 255)
    private String photo; 

    @Column(length = 100)
    private String activite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gestionnaire_id")
    @JsonIgnoreProperties({"password", "roles", "hibernateLazyInitializer", "handler"})
    private User gestionnaire;

    @OneToMany(mappedBy = "proprietaire", cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonIgnoreProperties("proprietaire")
    @Builder.Default
    private List<DossierSoins> dossiers = new ArrayList<>();
}
