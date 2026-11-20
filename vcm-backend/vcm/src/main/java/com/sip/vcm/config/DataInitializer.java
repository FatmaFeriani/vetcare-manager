package com.sip.vcm.config;

import com.sip.vcm.entities.Role;
import com.sip.vcm.entities.RoleName;
import com.sip.vcm.entities.User;
import com.sip.vcm.repositories.RoleRepository;
import com.sip.vcm.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Create roles if not exist
        if (!roleRepository.existsByLibelle(RoleName.ADMIN)) {
            roleRepository.save(Role.builder().libelle(RoleName.ADMIN).build());
        }
        if (!roleRepository.existsByLibelle(RoleName.COLLABORATEUR)) {
            roleRepository.save(Role.builder().libelle(RoleName.COLLABORATEUR).build());
        }

        // Create default admin if not exist
        if (!userRepository.existsByLogin("adminpethealth@gmail.com")) {
            Role adminRole = roleRepository.findByLibelle(RoleName.ADMIN).orElseThrow();
            User admin = User.builder()
                    .login("adminpethealth@gmail.com")
                    .password(passwordEncoder.encode("admin123"))
                    .nom("Admin")
                    .prenom("VetCare")
                    .tel("+21600000000")
                    .roles(Set.of(adminRole))
                    .build();
            userRepository.save(admin);
            System.out.println(">>> Compte admin créé : adminpethealth@gmail.com / admin123");
        }
    }
}
