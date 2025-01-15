package org.example.domain.service;

import org.example.domain.entity.Sala;
import org.example.domain.enums.Periodo;
import org.example.domain.rest.dto.CompleteSalaDTO;

import java.util.List;

public interface SalaService {

    Integer save(CompleteSalaDTO salaDTO);
    Sala findById(Integer id);
    CompleteSalaDTO findByIdReturnDTO(Integer id);
    List<CompleteSalaDTO> findByPeriodo(String periodo);
    List<CompleteSalaDTO> findAll();
    Sala update(Integer id, Sala sala);
    void deleteById(Integer id);
}
