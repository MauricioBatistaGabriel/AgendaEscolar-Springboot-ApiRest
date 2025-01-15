package org.example.domain.entity;

import lombok.*;
import org.example.domain.enums.Periodo;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity(name = "HORAAULA")
public class HoraAula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String horaInicial;
    private String horaFinal;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "Horaaula_periodo",
    joinColumns =
    @JoinColumn(name = "horaaula_periodo"))
    @Column(name = "PERIODO")
    private Periodo periodoDaHoraAula;

    private boolean isPresent;
}
