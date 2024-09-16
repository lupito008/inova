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
public class VotosPopular {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Colaborador colaborador;

    private Float nota;

    @ManyToOne
    private Eventos eventos;

    @ManyToOne
    private Ideias ideias;
}
