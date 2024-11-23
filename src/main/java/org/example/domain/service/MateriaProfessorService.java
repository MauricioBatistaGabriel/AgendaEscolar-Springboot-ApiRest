package org.example.domain.service;

import org.example.domain.entity.Materia;
import org.example.domain.entity.MateriaProfessor;
import org.example.domain.rest.dto.CompleteMateriaProfessorDTO;
import java.util.List;

public interface MateriaProfessorService {

    Integer save(CompleteMateriaProfessorDTO materiaProfessorDTO);

    List<Materia> findMateriasByProfessorId(Integer id);
    List<MateriaProfessor> findMateriaProfessorByIdMateria(Integer id);
    List<MateriaProfessor> findMateriaProfessorByIdProfessor(Integer id);
}
