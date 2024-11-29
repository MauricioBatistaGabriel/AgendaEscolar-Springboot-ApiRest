package org.example.domain.service;

import org.example.domain.entity.AlunoTurma;
import org.example.domain.rest.dto.CompleteAlunoTurmaDTO;

import java.util.List;

public interface AlunoTurmaService {

    Integer save(CompleteAlunoTurmaDTO alunoTurmaDTO);
}
