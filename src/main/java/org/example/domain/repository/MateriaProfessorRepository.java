package org.example.domain.repository;

import org.example.domain.entity.Materia;
import org.example.domain.entity.MateriaProfessor;
import org.example.domain.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import javax.transaction.Transactional;
import java.util.List;

public interface MateriaProfessorRepository extends JpaRepository<MateriaProfessor, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE " +
            "FROM MATERIA_PROFESSOR MP " +
            "WHERE MP.materia.id = :materia_id AND " +
            "MP.professor.id = :professor_id")
    void deleteByMateriaIdAndProfessorId(@Param("materia_id") Integer materiaId, @Param("professor_id") Integer professorId);
}
