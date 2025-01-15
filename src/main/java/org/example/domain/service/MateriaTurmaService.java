package org.example.domain.service;

import org.example.domain.entity.MateriaTurma;
import org.example.domain.rest.dto.CompleteMateriaTurmaDTO;

import java.util.List;

public interface MateriaTurmaService {

    Integer save(CompleteMateriaTurmaDTO materiaTurmaDTO);
    void deleteByMateriaIdAndTurmaId(Integer materiaId, Integer turmaId);
}
