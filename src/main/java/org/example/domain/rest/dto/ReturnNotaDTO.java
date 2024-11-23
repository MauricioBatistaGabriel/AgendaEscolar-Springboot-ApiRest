package org.example.domain.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnNotaDTO {

    private Integer nota;
    private ReturnAvaliacaoDTO avaliacao;
    private CompleteAlunoDTO aluno;
}
