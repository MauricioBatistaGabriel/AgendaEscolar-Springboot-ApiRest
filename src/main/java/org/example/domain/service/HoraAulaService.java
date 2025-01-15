package org.example.domain.service;

import org.example.domain.entity.HoraAula;
import org.example.domain.rest.dto.CompleteHoraAulaDTO;
import org.example.domain.rest.dto.ReturnHoraAulaDTO;

import java.util.List;

public interface HoraAulaService {

    Integer save(CompleteHoraAulaDTO horaAulaDTO);
    HoraAula findById(Integer id);
    CompleteHoraAulaDTO findByIdReturnDTO(Integer id);
    List<ReturnHoraAulaDTO> findAll();
    List<ReturnHoraAulaDTO> findByTurma(Integer id);
    ReturnHoraAulaDTO update(Integer id, CompleteHoraAulaDTO horaAula);
    void deleteById(Integer id);
}
