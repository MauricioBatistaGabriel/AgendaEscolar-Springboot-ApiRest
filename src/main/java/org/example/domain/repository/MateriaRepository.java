package org.example.domain.repository;

import org.example.domain.entity.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MateriaRepository extends JpaRepository<Materia, Integer> {

    @Query("SELECT m " +
            "FROM MATERIA m " +
            "JOIN MATERIA_TURMA mt ON m.id = mt.materia.id " +
            "JOIN MATERIA_PROFESSOR mp ON m.id = mp.materia.id " +
            "WHERE mt.turma.id = :id_turma " +
            "AND mp.professor.id = :id_professor")
    List<Materia> findByIdTurmaAndIdProfessor(@Param("id_turma") Integer idTurma, @Param("id_professor") Integer idProfessor);

    @Query("SELECT M " +
            "FROM MATERIA M " +
            "JOIN MATERIA_PROFESSOR MP ON MP.professor.id = :id_professor")
    List<Materia> findMateriasByProfessorId(@Param("id_professor") Integer idProfessor);
}
