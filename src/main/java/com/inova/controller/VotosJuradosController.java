package com.inova.controller;

import com.inova.entity.VotosJurados;
import com.inova.service.VotosJuradosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/votos-jurados")
public class VotosJuradosController {

    @Autowired
    private VotosJuradosService service;

    @GetMapping
    public ResponseEntity<List<VotosJurados>> getAll() {
        try {
            List<VotosJurados> votos = service.getAll();
            return ResponseEntity.ok(votos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<VotosJurados> getById(@PathVariable Long id) {
        try {
            VotosJurados voto = service.getById(id);
            return ResponseEntity.ok(voto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/colaborador/{colaboradorId}")
    public ResponseEntity<List<VotosJurados>> getByColaborador(@PathVariable Long colaboradorId) {
        try {
            List<VotosJurados> votos = service.getByColaborador(colaboradorId);
            return ResponseEntity.ok(votos);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<VotosJurados>> getByEvento(@PathVariable Long eventoId) {
        try {
            List<VotosJurados> votos = service.getByEvento(eventoId);
            return ResponseEntity.ok(votos);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<VotosJurados> create(@Valid @RequestBody VotosJurados votosJurados) {
        try {
            VotosJurados savedVotos = service.save(votosJurados);
            return ResponseEntity.ok(savedVotos);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<VotosJurados> update(@Valid @RequestBody VotosJurados votosJurados, @PathVariable Long id) {
        try {
            VotosJurados updatedVotos = service.update(id, votosJurados);
            return ResponseEntity.ok(updatedVotos);
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
