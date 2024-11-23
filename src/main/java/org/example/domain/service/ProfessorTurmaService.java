package org.example.domain.service;

import org.example.domain.entity.Professor;
import org.example.domain.rest.dto.CompleteProfessorTurmaDTO;

import java.util.List;

public interface ProfessorTurmaService{

    Integer save(CompleteProfessorTurmaDTO professorTurmaDTO);
    List<Professor> findProfessoresByIdTurma(Integer id);
}
