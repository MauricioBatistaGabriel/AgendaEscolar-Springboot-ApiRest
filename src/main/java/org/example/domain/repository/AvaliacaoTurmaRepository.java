package org.example.domain.repository;

import org.example.domain.entity.AvaliacaoTurma;
import org.example.domain.entity.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AvaliacaoTurmaRepository extends JpaRepository<AvaliacaoTurma, Integer> {

    @Query("SELECT at.turma FROM AVALIACAO_TURMA at WHERE at.avaliacao.id = :id")
    Turma findTurmaByIdAvaliacao(@Param("id") Integer id);

    AvaliacaoTurma findByAvaliacaoId(@Param("id") Integer id);

    List<AvaliacaoTurma> findByTurmaId(@Param("id") Integer id);
}
