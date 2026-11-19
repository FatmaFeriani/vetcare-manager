package com.sip.vcm.services;

import com.sip.vcm.dto.UserDTO;
import com.sip.vcm.entities.Role;
import com.sip.vcm.entities.RoleName;
import com.sip.vcm.entities.User;
import com.sip.vcm.exceptions.BadRequestException;
import com.sip.vcm.exceptions.ResourceNotFoundException;
import com.sip.vcm.repositories.RoleRepository;
import com.sip.vcm.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'id : " + id));
        return toDTO(user);
    }

    @Transactional
    public UserDTO updateRoles(Long id, List<String> roleNames) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'id : " + id));

        Set<Role> roles = new HashSet<>();
        for (String roleName : roleNames) {
            RoleName rn;
            try {
                rn = RoleName.valueOf(roleName.toUpperCase().replace("ROLE_", ""));
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Rôle invalide : " + roleName);
            }
            Role role = roleRepository.findByLibelle(rn)
                    .orElseThrow(() -> new BadRequestException("Rôle " + rn + " non trouvé"));
            roles.add(role);
        }

        user.setRoles(roles);
        userRepository.save(user);
        return toDTO(user);
    }

    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Utilisateur non trouvé avec l'id : " + id);
        }
        userRepository.deleteById(id);
    }

    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setLogin(user.getLogin());
        dto.setNom(user.getNom());
        dto.setPrenom(user.getPrenom());
        dto.setTel(user.getTel());
        dto.setRoles(user.getRoles().stream()
                .map(r -> r.getLibelle().name())
                .collect(Collectors.toList()));
        return dto;
    }
}
