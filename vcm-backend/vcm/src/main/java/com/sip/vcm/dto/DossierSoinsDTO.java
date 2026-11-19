package com.sip.vcm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class DossierSoinsDTO {

    private Long id;

    @NotBlank
    private String libelle;

    @NotNull
    @Positive(message = "La durée doit être strictement positive")
    private Integer duree;

    @NotNull
    @Positive(message = "Le budget doit être strictement positif")
    private Double budget;

    private String description;

    @NotNull(message = "Le propriétaire est obligatoire")
    private Long proprietaireId;
}
