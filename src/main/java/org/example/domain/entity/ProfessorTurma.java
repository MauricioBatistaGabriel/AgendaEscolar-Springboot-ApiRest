package org.example.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity(name = "PROFESSOR_TURMA")
@Getter
@Setter
@NoArgsConstructor
public class ProfessorTurma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_professor_turma")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_professor")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "id_turma")
    private Turma turma;

    private Boolean isPresent = true;

    public ProfessorTurma(Professor professor, Turma turma) {
        this.professor = professor;
        this.turma = turma;
        this.isPresent = true;
    }
}
