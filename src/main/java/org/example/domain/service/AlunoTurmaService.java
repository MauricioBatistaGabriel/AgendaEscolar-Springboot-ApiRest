package org.example.domain.service;

import org.example.domain.entity.AlunoTurma;
import org.example.domain.rest.dto.CompleteAlunoTurmaDTO;

import java.util.List;

public interface AlunoTurmaService {

    Integer save(CompleteAlunoTurmaDTO alunoTurmaDTO);

    AlunoTurma findAlunoTurmaByIdAluno(Integer id);

    List<AlunoTurma> findAlunoTurmaByIdTurma(Integer id);
}
