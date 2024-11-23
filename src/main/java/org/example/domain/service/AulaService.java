package org.example.domain.service;

import org.example.domain.entity.Aula;
import org.example.domain.rest.dto.CompleteAulaDTO;
import org.example.domain.rest.dto.ReturnAulaDTO;

import java.util.List;

public interface AulaService {

    Integer save(CompleteAulaDTO aulaDTO);
    Aula findById(Integer id);
    ReturnAulaDTO findByIdReturnDTO(Integer id);
    List<ReturnAulaDTO> filterAll(CompleteAulaDTO aulaDTO);
    Aula update(Integer id, Aula aula);
    void deleteById(Integer id);
}
