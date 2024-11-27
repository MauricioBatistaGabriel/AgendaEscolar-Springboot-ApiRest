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
}
