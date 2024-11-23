package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteMateriaDTO {

    @NotEmpty(message = "{campo.nome-materia}")
    @Size(min = 2, max = 40, message = "{campo.nome-materia.validation}")
    private String nome;
}
