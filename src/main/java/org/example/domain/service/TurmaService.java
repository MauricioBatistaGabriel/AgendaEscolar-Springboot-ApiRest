package org.example.domain.service;

import org.example.domain.entity.Turma;
import org.example.domain.rest.dto.CompleteTurmaDTO;
import org.example.domain.rest.dto.ReturnTurmaDTO;
import org.example.domain.rest.dto.ReturnTurmaInOtherClassDTO;
import org.example.domain.rest.dto.UpdateTurmaDTO;

import java.util.List;

public interface TurmaService {

    Integer save(CompleteTurmaDTO turmaDTO);
    Turma findById(Integer id);
    ReturnTurmaDTO findByIdReturnDTO(Integer id);
    ReturnTurmaInOtherClassDTO findByIdTurmaInOtherClass(Integer id);
    ReturnTurmaInOtherClassDTO findTurmaByIdAvaliacao(Integer id);
    List<Turma> findByMateriaId(Integer id);
    Turma findByAlunoId(Integer id);
    List<ReturnTurmaDTO> findAll();
    ReturnTurmaDTO update(Integer id, UpdateTurmaDTO turma);
    void deleteById(Integer id);
}
