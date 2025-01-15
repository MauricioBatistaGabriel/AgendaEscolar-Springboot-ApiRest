package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnAulaDTO {

    private Integer id;
    private ReturnTurmaInOtherClassDTO turma;
    private ReturnMateriaDTO materia;
    private ReturnProfessorDTOInAula professor;
    private ReturnHoraAulaDTO horaaula;
    private String data;
}
