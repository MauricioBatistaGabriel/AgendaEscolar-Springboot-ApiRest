package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.enums.Periodo;

import java.awt.event.PaintEvent;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfessorDTO {

    private String nome;
    private String cpf;
    private Set<Periodo> periodosDeTrabalho;
    private List<Integer> materias;
}
