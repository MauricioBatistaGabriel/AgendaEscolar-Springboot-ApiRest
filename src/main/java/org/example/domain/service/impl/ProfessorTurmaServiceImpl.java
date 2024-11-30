package org.example.domain.service.impl;

import org.example.domain.entity.Professor;
import org.example.domain.entity.ProfessorTurma;
import org.example.domain.entity.Turma;
import org.example.domain.repository.ProfessorTurmaRepository;
import org.example.domain.rest.dto.CompleteProfessorTurmaDTO;
import org.example.domain.service.ProfessorService;
import org.example.domain.service.ProfessorTurmaService;
import org.example.domain.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorTurmaServiceImpl implements ProfessorTurmaService {

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private TurmaService turmaService;

    @Autowired
    private ProfessorTurmaRepository professorTurmaRepository;

    @Override
    public Integer save(CompleteProfessorTurmaDTO professorTurmaDTO) {
        Professor professor = professorService.findById(professorTurmaDTO.getProfessor());

        Turma turma = turmaService.findById(professorTurmaDTO.getTurma());

        ProfessorTurma professorTurma = new ProfessorTurma(professor, turma);
        return professorTurmaRepository.save(professorTurma).getId();
    }
}
