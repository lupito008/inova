package com.inova.service;

import com.inova.entity.Eventos;
import com.inova.entity.VotosJurados;
import com.inova.entity.VotosPopular;
import com.inova.repository.EventoRepository;
import com.inova.repository.VotosJuradosRepository;
import com.inova.repository.VotosPopularRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventoService {

    @Autowired
    private EventoRepository repository;

    @Autowired
    private VotosJuradosRepository votosJuradosRepository;

    @Autowired
    private VotosPopularRepository votosPopularRepository;

    public List<Eventos> getAll() {
        return repository.findAll();
    }

    public Eventos getById(Long id) {
        Optional<Eventos> evento = repository.findById(id);
        return evento.orElseThrow(() -> new RuntimeException("Evento não encontrado"));
    }

    public List<Eventos> getByNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Eventos> getTop10EventosPorVotosJurados() {
        // Obter todos os eventos
        List<Eventos> todosEventos = repository.findAll();

        // Mapear os eventos para uma estrutura que armazene a média das notas dos votos jurados
        Map<Eventos, Double> mediaNotas = new HashMap<>();

        for (Eventos evento : todosEventos) {
            List<VotosJurados> votos = votosJuradosRepository.findByEventos(evento);
            double media = votos.stream()
                    .mapToDouble(VotosJurados::getNota)
                    .average()
                    .orElse(0.0);
            mediaNotas.put(evento, media);
        }

        // Ordenar os eventos pela média das notas, em ordem decrescente
        return mediaNotas.entrySet()
                .stream()
                .sorted(Map.Entry.<Eventos, Double>comparingByValue().reversed())
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<Eventos> getTop10EventosPorVotosPopulares() {
        // Obter todos os eventos
        List<Eventos> todosEventos = repository.findAll();

        // Mapear os eventos para uma estrutura que armazene a soma dos votos populares
        Map<Eventos, Double> totalVotos = new HashMap<>();

        for (Eventos evento : todosEventos) {
            List<VotosPopular> votos = votosPopularRepository.findByEventos(evento);
            double somaVotos = votos.stream()
                    .mapToDouble(voto -> voto.getNota() != null ? voto.getNota().doubleValue() : 0.0)  // Converte Float para Double
                    .sum();
            totalVotos.put(evento, somaVotos);
        }

        // Ordenar os eventos pela soma dos votos, em ordem decrescente
        return totalVotos.entrySet()
                .stream()
                .sorted(Map.Entry.<Eventos, Double>comparingByValue().reversed())
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }





    public Eventos save(Eventos evento) {
        return repository.save(evento);
    }

    public Eventos update(Long id, Eventos evento) {
        if (repository.existsById(id)) {
            evento.setId(id);
            return repository.save(evento);
        } else {
            throw new RuntimeException("Evento não encontrado");
        }
    }

    public String delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Evento deletado com sucesso";
        } else {
            throw new RuntimeException("Evento não encontrado");
        }
    }
}
