package org.example.domain.repository;

import org.example.domain.entity.Materia;
import org.example.domain.rest.dto.ReturnMateriaDTO;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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
            "JOIN MATERIA_TURMA MT ON MT.materia.id = M.id " +
            "WHERE MT.turma.id = :id_turma AND " +
            "MT.isPresent = true")
    List<Materia> findByTurmaId(@Param("id_turma") Integer idTurma);

    @Query("SELECT M " +
            "FROM MATERIA M " +
            "JOIN MATERIA_PROFESSOR MP ON MP.materia.id = M.id " +
            "WHERE MP.professor.id = :id_professor AND " +
            "MP.isPresent = true AND " +
            "M.isPresent = true")
    List<Materia> findByProfessorId(@Param("id_professor") Integer idProfessor);

    @Query("SELECT M " +
            "FROM MATERIA M " +
            "WHERE M.isPresent = true " +
            "ORDER BY M.id DESC")
    List<Materia> findAllOrderByIdDesc();
}
