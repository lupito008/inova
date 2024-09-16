package com.inova.repository;

import com.inova.entity.Colaborador;
import com.inova.entity.enums.PerfilUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {


    List<Colaborador> findByPerfilUsuario(PerfilUsuario perfilUsuario);

}
