package org.example.domain.repository;

import org.example.domain.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

    @Query("SELECT A " +
            "FROM ALUNO A " +
            "WHERE A.email = :email AND " +
            "A.isPresent = true")
    Optional<Aluno> findByEmail(@Param("email") String email);

    @Query("SELECT A " +
            "FROM ALUNO A " +
            "WHERE A.isPresent = true " +
            "ORDER BY A.id DESC")
    List<Aluno> findAllOrderByIdDesc();

    @Query("SELECT DISTINCT A " +
            "FROM ALUNO A " +
            "LEFT JOIN ALUNO_TURMA AT ON AT.aluno.id = A.id AND AT.isPresent = true " +
            "LEFT JOIN TURMA T ON T.id = AT.turma.id " +
            "WHERE " +
            "T.id IS NULL OR " +
            "T.isPresent <> true")
    List<Aluno> findSemTurma();
}
