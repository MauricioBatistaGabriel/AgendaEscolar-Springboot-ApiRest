package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveMateriaProfessorDTO {

    @NotNull(message = "{campo.materia}")
    private Integer materia;

    @NotNull(message = "{campo.professor}")
    private Integer Professor;
}
