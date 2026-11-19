package com.sip.vcm.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "dossiers_soins")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DossierSoins {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String libelle; // ex. « Vaccination annuelle de Rex »

    @Positive(message = "La durée doit être strictement positive")
    @Column(nullable = false)
    private Integer duree; 

    @Positive(message = "Le budget doit être strictement positif")
    @Column(nullable = false)
    private Double budget; // coût en TND

    @Column(columnDefinition = "TEXT")
    private String description; // espèce, race, symptômes, remarques

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "proprietaire_id", nullable = false)
    @JsonIgnoreProperties({"dossiers", "gestionnaire", "hibernateLazyInitializer", "handler"})
    private Proprietaire proprietaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gestionnaire_id")
    @JsonIgnoreProperties({"password", "roles", "hibernateLazyInitializer", "handler"})
    private User gestionnaire;
}
