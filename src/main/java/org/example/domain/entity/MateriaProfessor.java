package org.example.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "MATERIA_PROFESSOR")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MateriaProfessor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_materia_professor")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_materia")
    private Materia materia;

    @ManyToOne
    @JoinColumn(name = "id_professor")
    private Professor professor;

    private boolean isPresent = true;

    public MateriaProfessor(Materia materia, Professor professor) {
        this.materia = materia;
        this.professor = professor;
    }
}
