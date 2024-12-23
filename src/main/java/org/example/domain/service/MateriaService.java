package org.example.domain.service;

import org.example.domain.entity.Materia;
import org.example.domain.rest.dto.CompleteMateriaDTO;
import org.example.domain.rest.dto.ReturnMateriaDTO;

import java.util.List;

public interface MateriaService {

    Integer save(CompleteMateriaDTO materiaDTO);
    Materia findById(Integer id);
    CompleteMateriaDTO findByIdReturnDTO(Integer id);
    List<CompleteMateriaDTO> findByIdTurma(Integer id);
    List<Materia> findByProfessorId(Integer id);
    List<CompleteMateriaDTO> findByIdTurmaAndIdProfessor(Integer idTurma, Integer idProfessor);
    List<ReturnMateriaDTO> findAll();
    Materia update(Integer id, Materia materia);
    void deleteById(Integer id);
}
