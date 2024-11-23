package org.example.domain.repository;

import org.example.domain.entity.Professor;
import org.example.domain.entity.ProfessorTurma;
import org.example.domain.entity.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfessorTurmaRepository extends JpaRepository<ProfessorTurma, Integer> {

    @Query("SELECT pt.professor FROM PROFESSOR_TURMA pt WHERE pt.turma.id = :id")
    List<Professor> findProfessoresByTurmaId(@Param("id") Integer id);
}
