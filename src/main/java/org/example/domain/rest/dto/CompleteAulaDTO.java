package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.enums.Periodo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteAulaDTO {

    @NotEmpty(message = "{campo.data}")
    private String data;

    @NotNull(message = "{campo.professor}")
    private Integer professor;

    @NotNull(message = "{campo.materia}")
    private Integer materia;

    @NotNull(message = "{campo.turma}")
    private Integer turma;

    @NotNull(message = "{campo.horaaula}")
    private Integer horaaula;
}
