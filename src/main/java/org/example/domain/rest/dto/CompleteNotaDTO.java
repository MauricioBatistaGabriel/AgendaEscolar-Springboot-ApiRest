package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompleteNotaDTO {

    private Integer nota;
    private Integer aluno;
    private Integer avaliacao;
}
