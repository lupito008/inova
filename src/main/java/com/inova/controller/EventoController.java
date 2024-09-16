package com.inova.controller;

import com.inova.entity.Eventos;
import com.inova.service.EventoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    @Autowired
    private EventoService service;

    @GetMapping
    public ResponseEntity<List<Eventos>> getAll() {
        try {
            List<Eventos> eventos = service.getAll();
            return ResponseEntity.ok(eventos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Eventos> getById(@PathVariable long id) {
        try {
            Eventos evento = service.getById(id);
            return ResponseEntity.ok(evento);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Eventos>> getByNome(@PathVariable String nome) {
        try {
            List<Eventos> eventos = service.getByNome(nome);
            return ResponseEntity.ok(eventos);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/top10/jurados")
    public ResponseEntity<List<Eventos>> getTop10EventosPorVotosJurados() {
        try {
            List<Eventos> top10Eventos = service.getTop10EventosPorVotosJurados();
            return ResponseEntity.ok(top10Eventos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/top10/populares")
    public ResponseEntity<List<Eventos>> getTop10EventosPorVotosPopulares() {
        try {
            List<Eventos> top10Eventos = service.getTop10EventosPorVotosPopulares();
            return ResponseEntity.ok(top10Eventos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }



    @PostMapping
    public ResponseEntity<Eventos> create(@Valid @RequestBody Eventos evento) {
        try {
            Eventos eventoSaved = service.save(evento);
            return ResponseEntity.ok(eventoSaved);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Eventos> update(@Valid @RequestBody Eventos evento, @PathVariable Long id) {
        try {
            Eventos eventoSaved = service.update(id, evento);
            return ResponseEntity.ok(eventoSaved);
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
