package org.example.domain.service;

import org.example.domain.entity.Professor;
import org.example.domain.enums.Periodo;
import org.example.domain.rest.dto.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ProfessorService {

    Integer save(CompleteProfessorDTO professorDTO);
    UserDetails autenticar(Professor professor);
    Professor findById(Integer id);
    ReturnProfessorDTO findByIdReturnDTO(Integer id);
    ReturnCompleteProfessorDTO findByIdReturnDTOComplete(Integer id);
    List<ReturnProfessorDTO> findProfessorDTOByMateriaAndPeriodo(Integer materia, Periodo periodo);
    List<ReturnProfessorDTOInAula> findByMateriaAndHoraAulaAndData(FilterProfessorAulaCadastroDTO professorAulaCadastroDTO);
    List<Professor> findByMateriaId(Integer id);
    List<Professor> findByTurmaId(Integer id);
    List<ReturnProfessorDTO> findAll();
    ReturnProfessorDTO update(Integer id, UpdateProfessorDTO updateProfessorDTO);
    void deleteById(Integer id);
}
