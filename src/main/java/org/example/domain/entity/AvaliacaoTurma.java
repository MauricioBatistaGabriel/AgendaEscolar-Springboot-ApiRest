package org.example.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "AVALIACAO_TURMA")
@NoArgsConstructor
@Getter
@Setter
public class AvaliacaoTurma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_avaliacao")
    private Avaliacao avaliacao;

    @ManyToOne
    @JoinColumn(name = "id_turma")
    private Turma turma;

    private boolean isPresent = true;

    public AvaliacaoTurma(Avaliacao avaliacao, Turma turma) {
        this.avaliacao = avaliacao;
        this.turma = turma;
    }
}
