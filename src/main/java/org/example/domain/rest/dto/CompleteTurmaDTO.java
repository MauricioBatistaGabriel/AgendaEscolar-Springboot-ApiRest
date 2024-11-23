package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.enums.Periodo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteTurmaDTO {

    @Size(min = 1, max = 15, message = "{campo.nome-turma.validation}")
    @NotEmpty(message = "{campo.nome-turma}")
    private String nome;

    @NotNull(message = "{campo.sala}")
    private Integer sala;

    @NotNull(message = "{campo.periodo}")
    private Periodo periodo;

    @NotNull(message = "{campo.materia}")
    private List<Integer> materias;

    private List<Integer> alunos;

    @NotNull(message = "{campo.professor}")
    private List<Integer> professores;
}
