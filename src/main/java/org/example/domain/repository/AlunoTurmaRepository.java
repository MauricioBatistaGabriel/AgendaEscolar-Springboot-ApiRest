package org.example.domain.repository;

import org.example.domain.entity.Aluno;
import org.example.domain.entity.AlunoTurma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlunoTurmaRepository extends JpaRepository<AlunoTurma, Integer> {

    List<AlunoTurma> findByTurmaId(@Param("id") Integer id);

    AlunoTurma findByAlunoId(@Param("id") Integer id);
}
