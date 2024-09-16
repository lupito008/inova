package com.inova.service;

import com.inova.entity.Colaborador;
import com.inova.entity.Ideias;
import com.inova.entity.VotosJurados;
import com.inova.repository.ColaboradorRepository;
import com.inova.repository.IdeiasRepository;
import com.inova.repository.VotosJuradosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IdeiasService {

    @Autowired
    private IdeiasRepository repository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private VotosJuradosRepository votosJuradosRepository;

    public List<Ideias> getAll() {
        return repository.findAll();
    }

    public Ideias getById(Long id) throws Exception {
        Optional<Ideias> ideia = repository.findById(id);
        return ideia.orElseThrow(() -> new Exception("Ideia não encontrada"));
    }

    public List<Ideias> getTop10Ideias() {
        // Obter todas as ideias
        List<Ideias> todasIdeias = repository.findAll();

        // Mapear as ideias para uma estrutura que armazene a média das notas
        Map<Ideias, Double> mediaNotas = new HashMap<>();

        for (Ideias ideia : todasIdeias) {
            List<VotosJurados> votos = votosJuradosRepository.findByIdeias(ideia);
            double media = votos.stream()
                    .mapToDouble(VotosJurados::getNota)
                    .average()
                    .orElse(0.0);
            mediaNotas.put(ideia, media);
        }

        // Ordenar as ideias pela média das notas, em ordem decrescente
        return mediaNotas.entrySet()
                .stream()
                .sorted(Map.Entry.<Ideias, Double>comparingByValue().reversed())
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }


    public Ideias save(Ideias ideia) {
        Ideias ideiaSaved = repository.save(ideia);
        for (int i = 0; i < ideia.getColaborador().size(); i++) {
            Colaborador colaborador = colaboradorRepository.findById(ideia.getColaborador().get(i).getId()).orElse(null);


            if (colaborador.getIdeias() != null) {
                throw new RuntimeException("Colaborador já tem ideias cadastradas.");
            }

            colaborador.setIdeias(ideiaSaved);
            colaboradorRepository.save(colaborador);
        }


        return ideiaSaved;
    }

    public Ideias update(Long id, Ideias novaIdeia) throws Exception {
        Ideias ideiaExistente = this.getById(id);
        ideiaExistente.setNome(novaIdeia.getNome());
        ideiaExistente.setImpacto(novaIdeia.getImpacto());
        ideiaExistente.setCustoEstimado(novaIdeia.getCustoEstimado());
        ideiaExistente.setDescricao(novaIdeia.getDescricao());
        ideiaExistente.setColaborador(novaIdeia.getColaborador());
        return repository.save(ideiaExistente);
    }

    public String delete(Long id) throws Exception {
        Ideias ideia = this.getById(id);
        repository.delete(ideia);
        return "Ideia deletada com sucesso";
    }
}
