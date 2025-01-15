package org.example.domain.repository;

import org.example.domain.entity.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TurmaRepository extends JpaRepository<Turma, Integer> {

    @Query("SELECT T " +
            "FROM TURMA T " +
            "JOIN MATERIA_TURMA MT ON MT.turma.id = T.id " +
            "WHERE MT.materia.id = :materia_id " +
            "AND MT.isPresent = true")
    List<Turma> findByMateriaId(@Param("materia_id") Integer id);

    @Query("SELECT T " +
            "FROM TURMA T " +
            "JOIN ALUNO_TURMA AT ON AT.turma.id = T.id " +
            "WHERE AT.aluno.id = :id_aluno AND " +
            "AT.isPresent = true")
    Optional<Turma> findByAlunoId(@Param("id_aluno") Integer id);

    @Query("SELECT T " +
            "FROM TURMA T " +
            "WHERE T.isPresent = true " +
            "ORDER BY T.id DESC")
    List<Turma> findAllOrderByIdDesc();
}
