package org.example.domain.service;

import org.example.domain.entity.Turma;
import org.example.domain.rest.dto.CompleteTurmaDTO;
import org.example.domain.rest.dto.ReturnTurmaDTO;
import org.example.domain.rest.dto.ReturnTurmaInOtherClassDTO;

import java.util.List;

public interface TurmaService {

    Integer save(CompleteTurmaDTO turmaDTO);
    Turma findById(Integer id);
    ReturnTurmaDTO findByIdReturnDTO(Integer id);
    ReturnTurmaInOtherClassDTO findByIdTurmaInOtherClass(Integer id);
    ReturnTurmaInOtherClassDTO findTurmaByIdAvaliacao(Integer id);
    List<ReturnTurmaDTO> filterAll(CompleteTurmaDTO turmaDTO);
    Turma update(Integer id, Turma turma);
    void deleteById(Integer id);
}
