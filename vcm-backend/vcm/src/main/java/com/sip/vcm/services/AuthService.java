package com.sip.vcm.services;

import com.sip.vcm.dto.*;
import com.sip.vcm.entities.Role;
import com.sip.vcm.entities.RoleName;
import com.sip.vcm.entities.User;
import com.sip.vcm.exceptions.BadRequestException;
import com.sip.vcm.repositories.RoleRepository;
import com.sip.vcm.repositories.UserRepository;
import com.sip.vcm.security.JwtUtils;
import com.sip.vcm.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final EmailService emailService;

    @Transactional
    public MessageResponse register(RegisterRequest request) {
        if (userRepository.existsByLogin(request.getLogin())) {
            throw new BadRequestException("Erreur : cet email est déjà utilisé !");
        }

        User user = User.builder()
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .tel(request.getTel())
                .build();

        Set<Role> roles = new HashSet<>();
        final RoleName roleName;
        if (request.getRole() != null && !request.getRole().isBlank()) {
            try {
                roleName = RoleName.valueOf(request.getRole().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Rôle invalide. Valeurs possibles : ADMIN, COLLABORATEUR");
            }
        } else {
            roleName = RoleName.COLLABORATEUR; // default
        }

        Role role = roleRepository.findByLibelle(roleName)
                .orElseThrow(() -> new BadRequestException("Rôle " + roleName + " non trouvé en base"));
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);

        // Send notification email to admin
        emailService.sendNewUserNotification(user.getLogin(), user.getNom(), user.getPrenom());

        return new MessageResponse("Utilisateur enregistré avec succès !");
    }

    public JwtResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return JwtResponse.builder()
                .token(jwt)
                .type("Bearer")
                .id(userDetails.getId())
                .login(userDetails.getLogin())
                .nom(userDetails.getNom())
                .prenom(userDetails.getPrenom())
                .roles(roles)
                .build();
    }
}
