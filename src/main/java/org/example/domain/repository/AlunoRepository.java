package org.example.domain.repository;

import org.example.domain.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

    Optional<Aluno> findByEmail(String email);
}
