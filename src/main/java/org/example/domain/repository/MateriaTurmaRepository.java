package org.example.domain.repository;

import org.example.domain.entity.Materia;
import org.example.domain.entity.MateriaTurma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface MateriaTurmaRepository extends JpaRepository<MateriaTurma, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE " +
            "FROM MATERIA_TURMA MT " +
            "WHERE MT.materia.id = :materia_id AND " +
            "MT.turma.id = :turma_id")
    void deleteByMateriaIdAndTurmaId(@Param("materia_id") Integer materiaId, @Param("turma_id") Integer turmaId);
}
