package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnTurmaInOtherClassDTO {

    private Integer id;
    private String nome;
    private CompleteSalaDTO sala;

    public ReturnTurmaInOtherClassDTO(String nome, CompleteSalaDTO sala){
        this.nome = nome;
        this.sala = sala;
    }
}
