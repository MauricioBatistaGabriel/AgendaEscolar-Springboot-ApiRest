package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompleteAvaliacaoDTO {

    private Integer numeroAvaliacao;

    private Integer materia;

    private Integer turma;
}
