package org.example.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity(name = "MATERIA_TURMA")
@Setter
@Getter
@NoArgsConstructor
public class MateriaTurma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_materia_turma")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_materia")
    private Materia materia;

    @ManyToOne
    @JoinColumn(name = "id_turma")
    private Turma turma;

    private boolean isPresent = true;

    public MateriaTurma(Materia materia, Turma turma) {
        this.materia = materia;
        this.turma = turma;
    }
}
