package org.example.domain.repository;

import org.example.domain.entity.Sala;
import org.example.domain.enums.Periodo;
import org.example.domain.rest.dto.CompleteSalaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalaRepository extends JpaRepository<Sala, Integer> {

    @Query("SELECT s " +
            "FROM SALA s " +
            "JOIN s.periodosDisponiveis sp " +
            "WHERE sp = :periodo AND " +
            "s.isPresent = true")
    List<Sala> findByPeriodo(@Param("periodo")Periodo periodo);

    @Query("SELECT S " +
            "FROM SALA S " +
            "WHERE S.isPresent = true " +
            "ORDER BY S.id DESC")
    List<Sala> findAllOrderByIdDesc();
}
