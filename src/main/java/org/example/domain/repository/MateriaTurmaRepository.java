package org.example.domain.repository;

import org.example.domain.entity.Materia;
import org.example.domain.entity.MateriaTurma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MateriaTurmaRepository extends JpaRepository<MateriaTurma, Integer> {

    @Query("SELECT mt.materia FROM MATERIA_TURMA mt WHERE mt.turma.id = :id")
    List<Materia> findMateriasByIdTurma(@Param("id") Integer id);

    List<MateriaTurma> findByMateriaId(@Param("id") Integer id);

    List<MateriaTurma> findByTurmaId(@Param("id") Integer id);
}
