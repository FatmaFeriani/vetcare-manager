package com.sip.vcm.repositories;

import com.sip.vcm.entities.Role;
import com.sip.vcm.entities.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByLibelle(RoleName libelle);
    boolean existsByLibelle(RoleName libelle);
}
