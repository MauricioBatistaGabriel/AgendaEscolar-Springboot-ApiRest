package org.example.domain.repository;

import org.example.domain.entity.AlunoTurma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AlunoTurmaRepository extends JpaRepository<AlunoTurma, Integer> {

    @Query("SELECT AT " +
            "FROM ALUNO_TURMA AT " +
            "LEFT JOIN TURMA T ON T.id = AT.turma.id " +
            "WHERE T.id = :id AND " +
            "AT.isPresent = true")
    List<AlunoTurma> findByTurmaId(@Param("id") Integer id);

    @Query("SELECT AT " +
            "FROM ALUNO_TURMA AT " +
            "WHERE AT.id = :id")
    Optional<AlunoTurma> findById(@Param("id") Integer id);
}
