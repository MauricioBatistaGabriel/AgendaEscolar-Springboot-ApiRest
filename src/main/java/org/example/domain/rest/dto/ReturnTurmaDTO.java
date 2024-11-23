package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class ReturnTurmaDTO {

    private String nome;

    private CompleteSalaDTO sala;

    private List<CompleteMateriaDTO> materias;

    private List<ReturnProfessorDTO> professores;

    public ReturnTurmaDTO() {
        CompleteSalaDTO salaDTO = new CompleteSalaDTO();
        this.sala = salaDTO;
    }
}
