package com.inova.service;

import com.inova.entity.Colaborador;
import com.inova.entity.enums.PerfilUsuario;
import com.inova.repository.ColaboradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class ColaboradorService {
    @Autowired
    private ColaboradorRepository repository;

    public List<Colaborador> getAll() {
        return repository.findAll();
    }

    public Colaborador getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Colaborador> getByPerfil(String perfilUsuario) {
        PerfilUsuario perfil = PerfilUsuario.valueOf(perfilUsuario.toUpperCase());
        return repository.findByPerfilUsuario(perfil);
    }

    public Colaborador save(Colaborador colaborador){
        return repository.save(colaborador);
    }

    public Colaborador update(Long id, Colaborador colaborador){
        Colaborador colaboradorBanco = repository.findById(id).orElse(null);
        Assert.notNull(colaboradorBanco, "usuário não encontrado!");
        colaborador.setId(id);
        return repository.save(colaborador);
    }

    public String delete(Long id){
        repository.deleteById(id);
        return "Deletado com sucesso!";
    }
}
