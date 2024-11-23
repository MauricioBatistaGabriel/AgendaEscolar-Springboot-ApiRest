package org.example.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.domain.enums.Periodo;
import org.example.domain.rest.dto.CompleteMateriaDTO;
import org.example.domain.rest.dto.CompleteSalaDTO;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "TURMA")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome_turma")
    private String nome;

    @ManyToOne
    @JoinColumn(name = "id_sala")
    private Sala sala;

    @Enumerated(EnumType.STRING)
    private Periodo periodo;

    private boolean isPresent = true;

    public Turma(String nome, Sala sala) {
        this.nome = nome;
        this.sala = sala;
    }

    public Turma(String nome, Sala sala, Periodo periodo) {
        this.nome = nome;
        this.sala = sala;
        this.periodo = periodo;
    }
}
