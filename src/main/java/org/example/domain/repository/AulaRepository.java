package org.example.domain.repository;

import org.example.domain.entity.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface AulaRepository extends JpaRepository<Aula, Integer> {

    @Query("SELECT a FROM AULA a WHERE a.professor.id = :id")
    public Optional<List<Aula>> findAulaByIdProfessor(@Param("id") Integer id);
}
