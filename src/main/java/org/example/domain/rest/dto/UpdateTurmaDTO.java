package org.example.domain.rest.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.enums.Periodo;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTurmaDTO {

    private String nome;
    private Integer sala;
    private Periodo periodo;
    private List<Integer> materias;
}
