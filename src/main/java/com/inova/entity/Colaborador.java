package com.inova.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inova.entity.enums.PerfilUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Colaborador {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotBlank
    private String nome;

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String senha;

    @NotNull
    private PerfilUsuario perfilUsuario;

    @ManyToOne
    @JsonIgnore
    private Ideias ideias;

}
