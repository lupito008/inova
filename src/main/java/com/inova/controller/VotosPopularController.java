package com.inova.controller;

import com.inova.entity.VotosPopular;
import com.inova.service.VotosPopularService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/votos-popular")
public class VotosPopularController {

    @Autowired
    private VotosPopularService service;

    @GetMapping
    public ResponseEntity<List<VotosPopular>> getAll() {
        try {
            List<VotosPopular> votosPopulares = service.getAll();
            return ResponseEntity.ok(votosPopulares);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<VotosPopular> getById(@PathVariable Long id) {
        try {
            VotosPopular votoPopular = service.getById(id);
            return votoPopular != null ? ResponseEntity.ok(votoPopular) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<VotosPopular> create(@Valid @RequestBody VotosPopular votosPopular) {
        try {
            VotosPopular votoPopularSaved = service.save(votosPopular);
            return ResponseEntity.ok(votoPopularSaved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<VotosPopular> update(@Valid @RequestBody VotosPopular votosPopular, @PathVariable Long id) {
        try {
            VotosPopular votoPopularUpdated = service.update(id, votosPopular);
            return votoPopularUpdated != null ? ResponseEntity.ok(votoPopularUpdated) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.delete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
