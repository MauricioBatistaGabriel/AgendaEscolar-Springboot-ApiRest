package org.example.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.domain.enums.Periodo;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
@Entity(name = "SALA")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sala")
    private Integer id;

    @Column(name = "numero_sala")
    private String sala;

    @ElementCollection(targetClass = Periodo.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "Sala_periodos",
        joinColumns =
    @JoinColumn(name = "sala_id"))
    @Column(name = "PERIODO")
    private Set<Periodo> periodosDisponiveis;

    private boolean isPresent = true;

    public Sala(String sala) {
        this.sala = sala;
        this.periodosDisponiveis = new HashSet<>();
        this.periodosDisponiveis.add(Periodo.matutino);
        this.periodosDisponiveis.add(Periodo.vespertino);
        this.periodosDisponiveis.add(Periodo.noturno);
    }
}
