package org.example.domain.service.impl;

import org.example.domain.entity.Materia;
import org.example.domain.entity.MateriaProfessor;
import org.example.domain.entity.Professor;
import org.example.domain.repository.MateriaProfessorRepository;
import org.example.domain.repository.MateriaRepository;
import org.example.domain.repository.ProfessorRepository;
import org.example.domain.rest.dto.CompleteMateriaProfessorDTO;
import org.example.domain.service.MateriaProfessorService;
import org.example.domain.service.MateriaService;
import org.example.domain.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class MateriaProfessorServiceImpl implements MateriaProfessorService {

    @Autowired
    private MateriaProfessorRepository materiaProfessorRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private MateriaService materiaService;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ProfessorService professorService;

    @Override
    public Integer save(CompleteMateriaProfessorDTO materiaProfessorDTO) {
        Materia materia = materiaService.findById(materiaProfessorDTO.getMateria());

        Professor professor = professorService.findById(materiaProfessorDTO.getProfessor());

        MateriaProfessor materiaProfessor = new MateriaProfessor(materia, professor);
        return materiaProfessorRepository.save(materiaProfessor).getId();
    }
}
