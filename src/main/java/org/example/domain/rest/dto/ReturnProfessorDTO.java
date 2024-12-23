package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.entity.Materia;
import org.example.domain.enums.Periodo;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnProfessorDTO {

    private Integer id;
    private String nome;
    private String cpf;
    private Set<Periodo> periodosDeTrabalho;
    private List<ReturnMateriaDTO> materias;

    public ReturnProfessorDTO(Integer id, String nome, String cpf, Set<Periodo> periodos){
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.periodosDeTrabalho = periodos;
    }
}
