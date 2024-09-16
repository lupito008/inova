package com.inova.service;

import com.inova.entity.Colaborador;
import com.inova.entity.Eventos;
import com.inova.entity.Ideias;
import com.inova.entity.VotosPopular;
import com.inova.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class VotosPopularService {

    @Autowired
    private VotosPopularRepository votosPopularRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private IdeiasRepository ideiasRepository;

    @Autowired
    private VotosJuradosRepository votosJuradosRepository;

    public List<VotosPopular> getAll() {
        return votosPopularRepository.findAll();
    }

    public VotosPopular getById(Long id) {
        return votosPopularRepository.findById(id).orElse(null);
    }

    public VotosPopular save(VotosPopular votosPopular) {

        Eventos eventos = votosPopular.getEventos();
        Colaborador colaborador = votosPopular.getColaborador();

        if (eventos != null) {
            Eventos eventSaved = eventoRepository.findById(eventos.getId()).orElse(null);
            Assert.notNull(eventSaved, "Evento nulo");

            colaborador = colaboradorRepository.findById(colaborador.getId()).orElse(null);
            Assert.notNull(colaborador, "Colaborador não pode ser nulo!");

            // Verificar se o popular já votou neste evento por meio de votos jurados
            if (votosJuradosRepository.existsByEventosAndColaborador(eventos, colaborador)) {
                throw new RuntimeException("Usuário já votou neste evento com votos jurados.");
            }

            // Verificar se o popular já votou nesta ideia por meio de votos jurados
            if (votosJuradosRepository.existsByIdeiasAndColaborador(votosPopular.getIdeias(), colaborador)) {
                throw new RuntimeException("Usuário já votou nesta ideia com votos jurados.");
            }

            // Atualizar a nota do evento
            if (eventSaved.getNota() != null) {
                Float nota = eventSaved.getNota();
                eventSaved.setNota(nota + votosPopular.getNota());
            } else {
                eventSaved.setNota(votosPopular.getNota());
            }
            eventoRepository.save(eventSaved);
        }

        Ideias ideias = votosPopular.getIdeias();

        if (ideias != null) {
            Ideias ideiaSaved = ideiasRepository.findById(ideias.getId()).orElse(null);
            Assert.notNull(ideiaSaved, "Ideia nula");

            // Atualizar a nota da ideia
            if (ideiaSaved.getNota() != null) {
                Float nota = ideiaSaved.getNota();
                ideiaSaved.setNota(nota + votosPopular.getNota());
            } else {
                ideiaSaved.setNota(votosPopular.getNota());
            }
            ideiasRepository.save(ideiaSaved);
        }

        return votosPopularRepository.save(votosPopular);
    }


    public VotosPopular update(Long id, VotosPopular votosPopular) {
        if (votosPopularRepository.existsById(id)) {
            votosPopular.setId(id);
            return votosPopularRepository.save(votosPopular);
        } else {
            return null;
        }
    }

    public String delete(Long id) {
        if (votosPopularRepository.existsById(id)) {
            votosPopularRepository.deleteById(id);
            return "Voto Popular deletado com sucesso.";
        } else {
            return "Voto Popular não encontrado.";
        }
    }
}
