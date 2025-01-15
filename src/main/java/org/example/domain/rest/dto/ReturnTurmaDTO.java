package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.enums.Periodo;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnTurmaDTO {

    private Integer id;
    private String nome;
    private Periodo periodo;
    private ReturnSalaDTO sala;
    private List<ReturnMateriaDTO> materias;
}
