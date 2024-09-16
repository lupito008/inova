package com.inova.controller;

import com.inova.entity.Colaborador;
import com.inova.service.ColaboradorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colaborador")
public class ColaboradorController {

    @Autowired
    private ColaboradorService service;

    @GetMapping
    public ResponseEntity<List<Colaborador>> getAll() {
        try {
            List<Colaborador> colaboradors = service.getAll();
            return  ResponseEntity.ok(colaboradors);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Colaborador> getById(@PathVariable Long id) {
        try {
            Colaborador colaborador = service.getById(id);
            return ResponseEntity.ok(colaborador);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{perfil}")
    public ResponseEntity<List<Colaborador>> getByPerfil(@PathVariable String perfil) {
        try {
            List<Colaborador> colaboradores = service.getByPerfil(perfil);
            return ResponseEntity.ok(colaboradores);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<Colaborador> create(@Valid @RequestBody Colaborador colaborador) {
        try {
            Colaborador colaboradorSaved = service.save(colaborador);
            return ResponseEntity.ok(colaboradorSaved);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Colaborador> update (@Valid @RequestBody Colaborador colaborador, @PathVariable Long id){
        try{
            Colaborador colaboradorSaved = service.update(id, colaborador);
            return ResponseEntity.ok(colaboradorSaved);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        try{
            return ResponseEntity.ok(service.delete(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
