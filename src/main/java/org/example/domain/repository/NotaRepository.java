package org.example.domain.repository;

import org.example.domain.entity.Aluno;
import org.example.domain.entity.Avaliacao;
import org.example.domain.entity.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotaRepository extends JpaRepository<Nota, Integer> {

    @Query("SELECT n.avaliacao FROM NOTA n WHERE n.id= :id")
    Avaliacao findAvalicaoByIdNota(@Param("id") Integer id);

    @Query("SELECT n.aluno FROM NOTA n WHERE n.id = :id")
    Aluno findAlunoByIdNota(@Param("id") Integer id);
}
