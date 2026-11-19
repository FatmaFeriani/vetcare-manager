package com.sip.vcm.services;

import com.sip.vcm.dto.DossierSoinsDTO;
import com.sip.vcm.entities.DossierSoins;
import com.sip.vcm.entities.Proprietaire;
import com.sip.vcm.entities.User;
import com.sip.vcm.exceptions.ResourceNotFoundException;
import com.sip.vcm.repositories.DossierSoinsRepository;
import com.sip.vcm.repositories.ProprietaireRepository;
import com.sip.vcm.repositories.UserRepository;
import com.sip.vcm.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DossierSoinsService {

    private final DossierSoinsRepository dossierSoinsRepository;
    private final ProprietaireRepository proprietaireRepository;
    private final UserRepository userRepository;

    public Page<DossierSoins> findAll(Pageable pageable) {
        return dossierSoinsRepository.findAll(pageable);
    }

    public Page<DossierSoins> search(String keyword, Pageable pageable) {
        if (keyword == null || keyword.isBlank()) {
            return dossierSoinsRepository.findAll(pageable);
        }
        return dossierSoinsRepository.searchByLibelle(keyword, pageable);
    }

    public DossierSoins findById(Long id) {
        return dossierSoinsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dossier de soins non trouvé avec l'id : " + id));
    }

    public List<DossierSoins> findByProprietaireId(Long proprietaireId) {
        // Verify proprietaire exists
        if (!proprietaireRepository.existsById(proprietaireId)) {
            throw new ResourceNotFoundException("Propriétaire non trouvé avec l'id : " + proprietaireId);
        }
        return dossierSoinsRepository.findByProprietaireId(proprietaireId);
    }

    public Page<DossierSoins> findByProprietaireId(Long proprietaireId, Pageable pageable) {
        if (!proprietaireRepository.existsById(proprietaireId)) {
            throw new ResourceNotFoundException("Propriétaire non trouvé avec l'id : " + proprietaireId);
        }
        return dossierSoinsRepository.findByProprietaireId(proprietaireId, pageable);
    }

    @Transactional
    public DossierSoins create(DossierSoinsDTO dto) {
        Proprietaire proprietaire = proprietaireRepository.findById(dto.getProprietaireId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Propriétaire non trouvé avec l'id : " + dto.getProprietaireId()));

        User currentUser = getCurrentUser();

        DossierSoins dossier = DossierSoins.builder()
                .libelle(dto.getLibelle())
                .duree(dto.getDuree())
                .budget(dto.getBudget())
                .description(dto.getDescription())
                .proprietaire(proprietaire)
                .gestionnaire(currentUser)
                .build();

        return dossierSoinsRepository.save(dossier);
    }

    @Transactional
    public DossierSoins update(Long id, DossierSoinsDTO dto) {
        DossierSoins dossier = findById(id);

        if (dto.getProprietaireId() != null && !dto.getProprietaireId().equals(dossier.getProprietaire().getId())) {
            Proprietaire newProprietaire = proprietaireRepository.findById(dto.getProprietaireId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Propriétaire non trouvé avec l'id : " + dto.getProprietaireId()));
            dossier.setProprietaire(newProprietaire);
        }

        dossier.setLibelle(dto.getLibelle());
        dossier.setDuree(dto.getDuree());
        dossier.setBudget(dto.getBudget());
        dossier.setDescription(dto.getDescription());

        return dossierSoinsRepository.save(dossier);
    }

    @Transactional
    public void delete(Long id) {
        DossierSoins dossier = findById(id);
        dossierSoinsRepository.delete(dossier);
    }

    public Double getTotalBudget() {
        return dossierSoinsRepository.sumAllBudgets();
    }

    private User getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur connecté non trouvé"));
    }
}
