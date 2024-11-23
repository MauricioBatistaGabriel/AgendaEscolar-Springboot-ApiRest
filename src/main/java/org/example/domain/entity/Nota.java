package org.example.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "NOTA")
@NoArgsConstructor
@Getter
@Setter
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nota")
    private Integer id;

    private Integer nota;

    @ManyToOne
    @JoinColumn(name = "id_aluno")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "id_avaliacao")
    private Avaliacao avaliacao;

    private boolean isPresent = true;

    public Nota(Integer nota, Aluno aluno, Avaliacao avaliacao) {
        this.nota = nota;
        this.aluno = aluno;
        this.avaliacao = avaliacao;
    }
}
