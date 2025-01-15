package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterProfessorAulaCadastroDTO {

    private Integer turma;
    private Integer materia;
    private Integer horaAula;
    private String data;
}
