package com.sip.vcm.services;

import com.sip.vcm.dto.ProprietaireDTO;
import com.sip.vcm.entities.Proprietaire;
import com.sip.vcm.entities.User;
import com.sip.vcm.exceptions.BadRequestException;
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

@Service
@RequiredArgsConstructor
public class ProprietaireService {

    private final ProprietaireRepository proprietaireRepository;
    private final DossierSoinsRepository dossierSoinsRepository;
    private final UserRepository userRepository;

    public Page<Proprietaire> findAll(Pageable pageable) {
        return proprietaireRepository.findAll(pageable);
    }

    public Page<Proprietaire> search(String keyword, Pageable pageable) {
        if (keyword == null || keyword.isBlank()) {
            return proprietaireRepository.findAll(pageable);
        }
        return proprietaireRepository.searchByNomOrTelephone(keyword, pageable);
    }

    public Proprietaire findById(Long id) {
        return proprietaireRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Propriétaire non trouvé avec l'id : " + id));
    }

    @Transactional
    public Proprietaire create(ProprietaireDTO dto) {
        User currentUser = getCurrentUser();

        Proprietaire proprietaire = Proprietaire.builder()
                .nom(dto.getNom())
                .telephone(dto.getTelephone())
                .email(dto.getEmail())
                .adresse(dto.getAdresse())
                .photo(dto.getPhoto())
                .activite(dto.getActivite())
                .gestionnaire(currentUser)
                .build();

        return proprietaireRepository.save(proprietaire);
    }

    @Transactional
    public Proprietaire update(Long id, ProprietaireDTO dto) {
        Proprietaire proprietaire = findById(id);

        proprietaire.setNom(dto.getNom());
        proprietaire.setTelephone(dto.getTelephone());
        proprietaire.setEmail(dto.getEmail());
        proprietaire.setAdresse(dto.getAdresse());
        if (dto.getPhoto() != null) {
            proprietaire.setPhoto(dto.getPhoto());
        }
        proprietaire.setActivite(dto.getActivite());

        return proprietaireRepository.save(proprietaire);
    }

    @Transactional
    public void delete(Long id) {
        Proprietaire proprietaire = findById(id);

        // RG4 : refuse deletion if dossiers still exist
        if (dossierSoinsRepository.existsByProprietaireId(id)) {
            throw new BadRequestException(
                    "Impossible de supprimer ce propriétaire : il possède encore des dossiers de soins. " +
                    "Supprimez d'abord les dossiers associés.");
        }

        proprietaireRepository.delete(proprietaire);
    }

    private User getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur connecté non trouvé"));
    }
}
