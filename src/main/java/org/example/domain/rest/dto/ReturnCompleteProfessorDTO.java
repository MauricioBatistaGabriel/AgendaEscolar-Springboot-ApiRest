package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.enums.Periodo;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnCompleteProfessorDTO {

    @Size(min = 3, max = 100, message = "{campo.nome.validation}")
    @NotEmpty(message = "{campo.nome}")
    private String nome;

    @CPF(message = "{campo.cpf.validation}")
    @NotEmpty(message = "{campo.cpf}")
    private String cpf;

    @NotNull(message = "{campo.materia}")
    private List<CompleteMateriaDTO> materias;

    @NotNull(message = "{campo.periodo}")
    private Set<Periodo> periodosDeTrabalho;
}
