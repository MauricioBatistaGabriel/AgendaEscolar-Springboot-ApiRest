package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnAvaliacaoDTO {

    private Integer numeroAvaliacao;

    private CompleteMateriaDTO materia;

    private ReturnTurmaInOtherClassDTO turma;
}
