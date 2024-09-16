package com.inova.repository;

import com.inova.entity.Eventos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Eventos, Long> {

    // Buscar eventos por nome (parcialmente correspondente, ignorando maiúsculas/minúsculas)
    List<Eventos> findByNomeContainingIgnoreCase(String nome);
}
