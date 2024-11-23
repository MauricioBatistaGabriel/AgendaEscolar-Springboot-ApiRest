package org.example.domain.service.impl;

import org.example.domain.entity.Materia;
import org.example.domain.entity.MateriaTurma;
import org.example.domain.entity.Turma;
import org.example.domain.repository.MateriaRepository;
import org.example.domain.repository.MateriaTurmaRepository;
import org.example.domain.repository.TurmaRepository;
import org.example.domain.rest.dto.CompleteMateriaTurmaDTO;
import org.example.domain.service.MateriaService;
import org.example.domain.service.MateriaTurmaService;
import org.example.domain.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class MateriaTurmaServiceImpl implements MateriaTurmaService{

    @Autowired
    private MateriaTurmaRepository materiaTurmaRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private MateriaService materiaService;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private TurmaService turmaService;

    @Override
    public Integer save(CompleteMateriaTurmaDTO materiaTurmaDTO) {
        Materia materia1 = materiaService.findById(materiaTurmaDTO.getMateria());

        Turma turma1 = turmaService.findById(materiaTurmaDTO.getTurma());

        MateriaTurma materiaTurma = new MateriaTurma(materia1, turma1);
        return materiaTurmaRepository.save(materiaTurma).getId();
    }

    @Override
    public List<MateriaTurma> findMateriaTurmaByMateriaId(Integer id) {
        Materia materia = materiaService.findById(id);

        List<MateriaTurma> materiaTurmaList = materiaTurmaRepository.findByMateriaId(materia.getId());

        return materiaTurmaList;
    }

    @Override
    public List<MateriaTurma> findMateriaTurmaByTurmaId(Integer id) {
        Turma turma = turmaService.findById(id);

        List<MateriaTurma> materiaTurmaList = materiaTurmaRepository.findByTurmaId(turma.getId());

        return materiaTurmaList;
    }
}
