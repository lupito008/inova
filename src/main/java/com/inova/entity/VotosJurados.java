package com.inova.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VotosJurados {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaborador colaborador;

    private Float nota;

    @ManyToOne
    @JoinColumn(name = "eventos_id")
    private Eventos eventos;

    @ManyToOne
    @JoinColumn(name = "ideias_id")
    private Ideias ideias;

}
