package com.inova.repository;

import com.inova.entity.Colaborador;
import com.inova.entity.Eventos;
import com.inova.entity.Ideias;
import com.inova.entity.VotosJurados;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VotosJuradosRepository extends JpaRepository<VotosJurados, Long> {
    List<VotosJurados> findByColaboradorId(Long colaboradorId);
    List<VotosJurados> findByEventosId(Long eventoId);
    List<VotosJurados> findByIdeias(Ideias ideias);
    List<VotosJurados> findByEventos(Eventos eventos);
    boolean existsByEventosAndColaborador(Eventos eventos, Colaborador colaborador);
    boolean existsByIdeiasAndColaborador(Ideias ideias, Colaborador colaborador);

}
