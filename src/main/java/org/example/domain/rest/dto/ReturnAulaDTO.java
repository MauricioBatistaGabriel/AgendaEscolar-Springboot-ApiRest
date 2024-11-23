package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnAulaDTO {

    private String data;
    private ReturnProfessorDTO professor;
    private CompleteMateriaDTO materia;
    private ReturnTurmaInOtherClassDTO turma;
}
