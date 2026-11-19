package com.sip.vcm.controllers;

import com.sip.vcm.dto.DossierSoinsDTO;
import com.sip.vcm.entities.DossierSoins;
import com.sip.vcm.services.DossierSoinsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class DossierSoinsController {

    private final DossierSoinsService dossierSoinsService;

    @GetMapping("/dossiers")
    public ResponseEntity<Page<DossierSoins>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false) String search) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<DossierSoins> result = (search != null && !search.isBlank())
                ? dossierSoinsService.search(search, pageable)
                : dossierSoinsService.findAll(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/dossiers/{id}")
    public ResponseEntity<DossierSoins> getById(@PathVariable Long id) {
        return ResponseEntity.ok(dossierSoinsService.findById(id));
    }

    @GetMapping("/proprietaires/{id}/dossiers")
    public ResponseEntity<List<DossierSoins>> getByProprietaire(@PathVariable Long id) {
        return ResponseEntity.ok(dossierSoinsService.findByProprietaireId(id));
    }

    @PostMapping("/dossiers")
    public ResponseEntity<DossierSoins> create(@Valid @RequestBody DossierSoinsDTO dto) {
        return ResponseEntity.ok(dossierSoinsService.create(dto));
    }

    @PutMapping("/dossiers/{id}")
    public ResponseEntity<DossierSoins> update(@PathVariable Long id, @Valid @RequestBody DossierSoinsDTO dto) {
        return ResponseEntity.ok(dossierSoinsService.update(id, dto));
    }

    @DeleteMapping("/dossiers/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dossierSoinsService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/dossiers/stats/budget")
    public ResponseEntity<Map<String, Double>> getTotalBudget() {
        return ResponseEntity.ok(Map.of("totalBudget", dossierSoinsService.getTotalBudget()));
    }
}
