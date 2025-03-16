package org.example.domain.service;

import org.example.domain.entity.AlunoTurma;
import org.example.domain.rest.dto.CompleteAlunoTurmaDTO;

import java.util.List;

public interface AlunoTurmaService {

    Integer save(AlunoTurma alunoTurma);
    List<AlunoTurma> findByTurmaId(Integer turmaId);
    AlunoTurma findById(Integer alunoTurmaId);
    AlunoTurma update(AlunoTurma alunoTurma);
    void delete(AlunoTurma alunoTurma);
}
