package org.example.domain.repository;

import org.example.domain.entity.UsuarioAdm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioAdmRepository extends JpaRepository<UsuarioAdm, Integer> {

    @Query("SELECT U " +
            "FROM USUARIOADM U " +
            "WHERE U.email = :email AND " +
            "U.isPresent = true")
    Optional<UsuarioAdm> findByEmail(@Param("email") String email);
}
