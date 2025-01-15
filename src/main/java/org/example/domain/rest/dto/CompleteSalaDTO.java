package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.enums.Periodo;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteSalaDTO {

    private Integer id;
    private String sala;
    private Set<Periodo> periodosDisponiveis;

    public CompleteSalaDTO(Integer id, String sala){
        this.id = id;
        this.sala = sala;
    }

    public CompleteSalaDTO(String sala, Set<Periodo> periodosDisponiveis){
        this.sala = sala;
        this.periodosDisponiveis = periodosDisponiveis;
    }
}
