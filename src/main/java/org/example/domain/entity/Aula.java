package org.example.domain.entity;

import lombok.*;
import org.example.domain.enums.Periodo;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
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

    @ManyToOne
    @JoinColumn(name = "id_horaaula")
    private HoraAula horaAula;

    private boolean isPresent = true;

    public Aula(String data, Professor professor, Materia materia, Turma turma, HoraAula horaAula) {
        this.data = data;
        this.professor = professor;
        this.materia = materia;
        this.turma = turma;
        this.horaAula = horaAula;
    }
}
