package com.inova.service;

import com.inova.entity.Colaborador;
import com.inova.entity.Eventos;
import com.inova.entity.Ideias;
import com.inova.entity.VotosJurados;
import com.inova.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class VotosJuradosService {

    @Autowired
    private VotosJuradosRepository repository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private IdeiasRepository ideiasRepository;

    @Autowired
    private VotosPopularRepository votosPopularRepository;

    // Obter todos os votos jurados
    public List<VotosJurados> getAll() {
        return repository.findAll();
    }

    // Obter voto jurado por ID
    public VotosJurados getById(Long id) {
        Optional<VotosJurados> voto = repository.findById(id);
        if (voto.isPresent()) {
            return voto.get();
        } else {
            throw new RuntimeException("VotoJurados não encontrado com ID: " + id);
        }
    }

    // Obter votos jurados por ID do colaborador
    public List<VotosJurados> getByColaborador(Long colaboradorId) {
        return repository.findByColaboradorId(colaboradorId);
    }

    // Obter votos jurados por ID do evento
    public List<VotosJurados> getByEvento(Long eventoId) {
        return repository.findByEventosId(eventoId);
    }

    public VotosJurados save(VotosJurados votosJurados) {

        Eventos eventos = votosJurados.getEventos();
        Colaborador colaborador = votosJurados.getColaborador();

        if (eventos != null) {
            Eventos eventSaved = eventoRepository.findById(eventos.getId()).orElse(null);
            Assert.notNull(eventSaved, "Evento nulo");

            colaborador = colaboradorRepository.findById(colaborador.getId()).orElse(null);
            Assert.notNull(colaborador, "Colaborador não pode ser nulo!");

            Assert.isTrue(eventSaved.getJurados().contains(colaborador), "Colaborador não está na lista de jurados.");

            // Verificar se o jurado já votou neste evento por meio de votos populares
            if (votosPopularRepository.existsByEventosAndColaborador(eventos, colaborador)) {
                throw new RuntimeException("Jurado já votou neste evento com votos populares.");
            }

            // Verificar se o jurado já votou nesta ideia por meio de votos populares
            if (votosPopularRepository.existsByIdeiasAndColaborador(votosJurados.getIdeias(), colaborador)) {
                throw new RuntimeException("Jurado já votou nesta ideia com votos populares.");
            }

            // Atualizar a nota do evento
            if (eventSaved.getNota() != null) {
                Float nota = eventSaved.getNota();
                eventSaved.setNota(nota + votosJurados.getNota());
            } else {
                eventSaved.setNota(votosJurados.getNota());
            }
            eventoRepository.save(eventSaved);
        }

        Ideias ideias = votosJurados.getIdeias();

        if (ideias != null) {
            Ideias ideiaSaved = ideiasRepository.findById(ideias.getId()).orElse(null);
            Assert.notNull(ideiaSaved, "Ideia nula");

            // Atualizar a nota da ideia
            if (ideiaSaved.getNota() != null) {
                Float nota = ideiaSaved.getNota();
                ideiaSaved.setNota(nota + votosJurados.getNota());
            } else {
                ideiaSaved.setNota(votosJurados.getNota());
            }
            ideiasRepository.save(ideiaSaved);
        }

        return repository.save(votosJurados);
    }



    public VotosJurados update(Long id, VotosJurados votosJurados) {
        if (repository.existsById(id)) {
            votosJurados.setId(id);
            return repository.save(votosJurados);
        } else {
            throw new RuntimeException("VotoJurados não encontrado com ID: " + id);
        }
    }

    // Excluir voto jurado
    public String delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "VotoJurados excluído com sucesso!";
        } else {
            throw new RuntimeException("VotoJurados não encontrado com ID: " + id);
        }
    }
}
