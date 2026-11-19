package com.sip.vcm.controllers;

import com.sip.vcm.dto.ProprietaireDTO;
import com.sip.vcm.entities.Proprietaire;
import com.sip.vcm.services.ProprietaireService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/proprietaires")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProprietaireController {

    private final ProprietaireService proprietaireService;

    @GetMapping
    public ResponseEntity<Page<Proprietaire>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false) String search) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<Proprietaire> result = (search != null && !search.isBlank())
                ? proprietaireService.search(search, pageable)
                : proprietaireService.findAll(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proprietaire> getById(@PathVariable Long id) {
        return ResponseEntity.ok(proprietaireService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Proprietaire> create(@Valid @RequestBody ProprietaireDTO dto) {
        return ResponseEntity.ok(proprietaireService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proprietaire> update(@PathVariable Long id, @Valid @RequestBody ProprietaireDTO dto) {
        return ResponseEntity.ok(proprietaireService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        proprietaireService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
