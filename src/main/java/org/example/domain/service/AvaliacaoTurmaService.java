package org.example.domain.service;

import org.example.domain.entity.AvaliacaoTurma;
import org.example.domain.rest.dto.CompleteAvaliacaoTurmaDTO;
import org.example.domain.rest.dto.ReturnTurmaInOtherClassDTO;

import java.util.List;

public interface AvaliacaoTurmaService {

    Integer save(CompleteAvaliacaoTurmaDTO avaliacaoTurmaDTO);

    AvaliacaoTurma findAvaliacaoTurmaByAvaliacaoId(Integer id);

    List<AvaliacaoTurma> findAvaliacaoTurmaByTurmaId(Integer id);
}
