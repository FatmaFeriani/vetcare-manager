package com.sip.vcm.controllers;

import com.sip.vcm.repositories.DossierSoinsRepository;
import com.sip.vcm.repositories.ProprietaireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class DashboardController {

    private final ProprietaireRepository proprietaireRepository;
    private final DossierSoinsRepository dossierSoinsRepository;

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("nombreProprietaires", proprietaireRepository.count());
        stats.put("nombreDossiers", dossierSoinsRepository.count());
        stats.put("budgetTotal", dossierSoinsRepository.sumAllBudgets());
        return ResponseEntity.ok(stats);
    }
}
