package com.sip.vcm.repositories;

import com.sip.vcm.entities.DossierSoins;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DossierSoinsRepository extends JpaRepository<DossierSoins, Long> {

    List<DossierSoins> findByProprietaireId(Long proprietaireId);

    Page<DossierSoins> findByProprietaireId(Long proprietaireId, Pageable pageable);

    @Query("SELECT d FROM DossierSoins d WHERE LOWER(d.libelle) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<DossierSoins> searchByLibelle(@Param("keyword") String keyword, Pageable pageable);

    Page<DossierSoins> findAll(Pageable pageable);

    boolean existsByProprietaireId(Long proprietaireId);

    @Query("SELECT COALESCE(SUM(d.budget), 0) FROM DossierSoins d")
    Double sumAllBudgets();
}
