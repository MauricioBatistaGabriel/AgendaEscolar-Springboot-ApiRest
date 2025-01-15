package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnAllAlunoDTO {

    private Integer id;
    private String nome;
    private String cpf;
    private String idade;
    private String turma;
}
