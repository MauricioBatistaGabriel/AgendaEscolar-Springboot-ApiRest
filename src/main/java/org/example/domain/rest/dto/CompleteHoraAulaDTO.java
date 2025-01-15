package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.enums.Periodo;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompleteHoraAulaDTO {

    private String horaInicial;
    private String horaFinal;
    private Periodo periodoDaHoraAula;
    private boolean isPresent;
}
