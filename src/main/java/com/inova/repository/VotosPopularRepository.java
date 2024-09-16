package com.inova.repository;

import com.inova.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VotosPopularRepository extends JpaRepository<VotosPopular, Long> {
    List<VotosPopular> findByEventos(Eventos eventos);
    boolean existsByEventosAndColaborador(Eventos eventos, Colaborador colaborador);
    boolean existsByIdeiasAndColaborador(Ideias ideias, Colaborador colaborador);

}
