package org.example.domain.service;

import org.example.domain.entity.Aula;
import org.example.domain.rest.dto.CompleteAulaDTO;
import org.example.domain.rest.dto.ReturnAulaDTO;
import org.example.domain.rest.dto.ReturnAulaInProfessorDTO;

import java.util.List;

public interface AulaService {

    Integer save(CompleteAulaDTO aulaDTO);
    Aula findById(Integer id);
    ReturnAulaDTO findByIdReturnDTO(Integer id);
    List<ReturnAulaInProfessorDTO> findByProfessorId(Integer id);
    List<ReturnAulaDTO> findAll();
    ReturnAulaDTO update(Integer id, CompleteAulaDTO aula);
    void deleteById(Integer id);
}
