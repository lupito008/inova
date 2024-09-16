package com.inova.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Eventos {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String nome;

    @Size(min = 5, max = 1000, message = "Limite de caracteres 1000!")
    private String descricao;

    @NotNull
    private LocalDateTime dataInicio;

    @NotNull
    private LocalDateTime dataFim;

    @NotNull
    private LocalDateTime dataAvaliacaoJurados;

    @NotNull
    private LocalDateTime dataAvaliacaoPopular;

    @NotNull
    @ManyToMany
    @JoinTable(
            name = "evento_jurados",
            joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "colaborador_id")
    )
    private List<Colaborador> jurados;

    private Float nota;
}
