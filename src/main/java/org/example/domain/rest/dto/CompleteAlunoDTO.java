package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteAlunoDTO {

    private String email;

    private String senha;

    private String nome;

    private String cpf;

    private String idade;

    public CompleteAlunoDTO(String nome, String cpf, String idade){
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
    }
}
