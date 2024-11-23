package org.example.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity(name = "AVALIACAO")
@Getter
@Setter
@NoArgsConstructor
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avaliacao")
    private Integer id;

    @Column(name = "numero_avaliacao")
    private Integer numeroAvaliacao;

    @ManyToOne
    @JoinColumn(name = "id_materia")
    private Materia materia;

    private boolean isPresent = true;

    public Avaliacao(Integer numeroAvaliacao, Materia materia) {
        this.numeroAvaliacao = numeroAvaliacao;
        this.materia = materia;
    }
}
