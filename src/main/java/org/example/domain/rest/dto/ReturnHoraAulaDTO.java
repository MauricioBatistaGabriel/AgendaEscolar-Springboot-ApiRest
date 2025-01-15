package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.enums.Periodo;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnHoraAulaDTO {

    private Integer id;
    private String horaInicial;
    private String horaFinal;
    private Periodo periodoDaHoraAula;
}
