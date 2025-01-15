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

    @Query("SELECT p FROM PROFESSOR p " +
            "JOIN MATERIA_PROFESSOR mp ON mp.professor.id = p.id " +
            "WHERE p.id NOT IN (" +
            " SELECT a.professor.id FROM AULA a " +
            " WHERE a.data = :data AND a.horaAula.id = :horaAulaId" +
            ") " + "AND mp.materia.id = :materiaId " +
            "AND :periodo MEMBER OF p.periodosDeTrabalho")
    List<Professor> findByMateriaAndHoraAulaAndData(@Param("data") String data,
                                                    @Param("horaAulaId") Integer horaAulaId,
                                                    @Param("materiaId") Integer materiaId,
                                                    @Param("periodo") Periodo periodo);

    @Query("SELECT P " +
            "FROM PROFESSOR P " +
            "WHERE P.email = :email AND " +
            "P.isPresent = true")
    Optional<Professor> findByEmail(@Param("email") String email);

    @Query("SELECT P " +
            "FROM PROFESSOR P " +
            "JOIN MATERIA_PROFESSOR MP ON MP.professor.id = P.id " +
            "WHERE MP.materia.id = :id_materia " +
            "AND MP.isPresent = true")
    List<Professor> findByMateriaId(@Param("id_materia") Integer idMateria);

    @Query("SELECT P " +
            "FROM PROFESSOR P " +
            "JOIN AULA A ON A.professor.id = P.id " +
            "JOIN TURMA T ON T.id = A.turma.id " +
            "WHERE T.id = :id_turma " +
            "AND P.isPresent = true " +
            "AND T.isPresent = true " +
            "AND A.isPresent = true")
    List<Professor> findByTurmaId(@Param("id_turma") Integer id);

    @Query("SELECT P " +
            "FROM PROFESSOR P " +
            "WHERE P.isPresent = true " +
            "ORDER BY P.id DESC")
    List<Professor> findAllOrderByIdDesc();

    @Query(value = "SELECT CASE WHEN STR_TO_DATE(:dataString, '%d/%m/%Y') >= CURDATE() THEN TRUE ELSE FALSE END FROM dual", nativeQuery = true)
    boolean possuiAulaAgendada(@Param("dataString") String dataString);
}
