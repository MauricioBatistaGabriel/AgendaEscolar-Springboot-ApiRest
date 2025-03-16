package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteAlunoTurmaDTO {

    private Integer aluno;

    private Integer turma;
    private boolean isPresent;
}
