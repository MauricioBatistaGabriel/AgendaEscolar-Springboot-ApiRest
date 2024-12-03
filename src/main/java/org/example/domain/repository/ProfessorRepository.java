package org.example.domain.repository;

import org.example.domain.entity.Professor;
import org.example.domain.enums.Periodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {

    @Query("SELECT p " +
            "FROM PROFESSOR p " +
            "JOIN MATERIA_PROFESSOR mp ON mp.materia.id = :idMateria " +
            "JOIN p.periodosDeTrabalho pt " +
            "WHERE pt = :periodo " +
            "AND p.isPresent = true " +
            "AND mp.materia.isPresent = true")
    List<Professor> findProfessorByPeriodoAndMateria(@Param("idMateria") Integer idMateria, @Param("periodo")Periodo periodo);

    Optional<Professor> findByEmail(String email);

    @Query("SELECT P " +
            "FROM PROFESSOR P " +
            "JOIN MATERIA_PROFESSOR MP ON MP.professor.id = P.id " +
            "WHERE MP.materia.id = :id_materia " +
            "AND MP.isPresent = true")
    List<Professor> findByMateriaId(@Param("id_materia") Integer idMateria);

    @Query("SELECT P " +
            "FROM PROFESSOR P " +
            "JOIN PROFESSOR_TURMA PT ON PT.professor.id = P.id " +
            "WHERE PT.turma.id = :id_turma " +
            "AND PT.isPresent = true")
    List<Professor> findByTurmaId(@Param("id_turma") Integer id);

    @Query("SELECT P " +
            "FROM PROFESSOR P " +
            "WHERE P.isPresent = true " +
            "ORDER BY P.id DESC")
    List<Professor> findAllOrderByIdDesc();

    @Query(value = "SELECT CASE WHEN STR_TO_DATE(:dataString, '%d/%m/%Y') >= CURDATE() THEN TRUE ELSE FALSE END FROM dual", nativeQuery = true)
    boolean possuiAulaAgendada(@Param("dataString") String dataString);
}
