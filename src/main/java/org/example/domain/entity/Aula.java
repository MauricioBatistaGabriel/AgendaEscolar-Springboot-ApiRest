package org.example.domain.entity;

import lombok.*;
import org.example.domain.enums.Periodo;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Setter
@Getter
@Entity(name = "AULA")
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aula")
    private Integer id;

    @Column(name = "data_da_aula")
    @NotEmpty(message = "{campo.data}")
    private String data;

    @ManyToOne
    @JoinColumn(name = "id_professor")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "id_materia")
    private Materia materia;

    @ManyToOne
    @JoinColumn(name = "id_turma")
    private Turma turma;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "Aula_periodo",
        joinColumns =
    @JoinColumn(name = "aula_id"))
    @Column(name = "PERIODO")
    private Periodo periodo;

    private boolean isPresent = true;

    public Aula(String data, Professor professor, Materia materia, Turma turma, Periodo periodo) {
        this.data = data;
        this.professor = professor;
        this.materia = materia;
        this.turma = turma;
        this.periodo = periodo;
    }
}
