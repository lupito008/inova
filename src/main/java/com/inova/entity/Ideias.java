package com.inova.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Ideias {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String impacto;

    @NotNull
    private Float custoEstimado;

    private Float nota;

    @NotNull
    @Size(min = 5, max = 1000, message = "Limite maximo 1000 caracteres.")
    private String Descricao;

    @NotNull
    @OneToMany
    @JoinTable(
            name = "colaboradores",
            joinColumns = @JoinColumn(name = "ideia_id"),
            inverseJoinColumns = @JoinColumn(name = "colaborador_id")
    )
    private List<Colaborador> colaborador;

}
