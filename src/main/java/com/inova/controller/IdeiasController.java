package com.inova.controller;

import com.inova.entity.Ideias;
import com.inova.service.IdeiasService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ideias")
public class IdeiasController {

    @Autowired
    private IdeiasService service;

    @GetMapping
    public ResponseEntity<List<Ideias>> getAll() {
        try {
            List<Ideias> ideias = service.getAll();
            return ResponseEntity.ok(ideias);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ideias> getById(@PathVariable long id) {
        try {
            Ideias ideia = service.getById(id);
            return ResponseEntity.ok(ideia);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Ideias> create(@Valid @RequestBody Ideias ideia) {
        try {
            Ideias ideiaSaved = service.save(ideia);
            return ResponseEntity.ok(ideiaSaved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/top10")
    public ResponseEntity<List<Ideias>> getTop10Ideias() {
        try {
            List<Ideias> top10Ideias = service.getTop10Ideias();
            return ResponseEntity.ok(top10Ideias);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Ideias> update(@Valid @RequestBody Ideias ideia, @PathVariable Long id) {
        try {
            Ideias ideiaUpdated = service.update(id, ideia);
            return ResponseEntity.ok(ideiaUpdated);
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
