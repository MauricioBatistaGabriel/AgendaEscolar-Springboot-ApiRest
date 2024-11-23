package org.example.domain.repository;

import org.example.domain.entity.Materia;
import org.example.domain.entity.MateriaProfessor;
import org.example.domain.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MateriaProfessorRepository extends JpaRepository<MateriaProfessor, Integer> {

    @Query("SELECT mp.materia FROM MATERIA_PROFESSOR mp WHERE mp.professor.id = :id")
    List<Materia> findMateriasByProfessorId(@Param("id") Integer id);

    List<MateriaProfessor> findByMateriaId(@Param("id") Integer id);

    List<MateriaProfessor> findByProfessorId(@Param("id") Integer id);
}
