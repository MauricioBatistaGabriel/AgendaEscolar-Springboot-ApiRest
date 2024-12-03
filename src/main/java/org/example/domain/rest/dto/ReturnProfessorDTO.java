package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.entity.Materia;
import org.example.domain.enums.Periodo;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnProfessorDTO {

    private Integer id;
    private String nome;
    private String cpf;
    private Set<Periodo> periodos;
}
