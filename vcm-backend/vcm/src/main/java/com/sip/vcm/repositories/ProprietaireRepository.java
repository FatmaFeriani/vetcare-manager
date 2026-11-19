package com.sip.vcm.repositories;

import com.sip.vcm.entities.Proprietaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProprietaireRepository extends JpaRepository<Proprietaire, Long> {

    @Query("SELECT p FROM Proprietaire p WHERE LOWER(p.nom) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR p.telephone LIKE CONCAT('%', :keyword, '%')")
    Page<Proprietaire> searchByNomOrTelephone(@Param("keyword") String keyword, Pageable pageable);

    Page<Proprietaire> findAll(Pageable pageable);
}
