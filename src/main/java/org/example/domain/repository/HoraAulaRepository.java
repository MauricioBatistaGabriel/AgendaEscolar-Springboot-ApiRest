package org.example.domain.repository;

import org.example.domain.entity.HoraAula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HoraAulaRepository extends JpaRepository<HoraAula, Integer> {

    @Query("SELECT HA " +
            "FROM HORAAULA HA " +
            "WHERE HA.isPresent = true " +
            "ORDER BY HA.id DESC")
    List<HoraAula> findAllOrderByIdDesc();

    @Query("SELECT ha " +
            "FROM HORAAULA ha " +
            "JOIN TURMA t ON ha.periodoDaHoraAula = t.periodo " +
            "WHERE t.id = :turmaId " +
            "AND ha.isPresent = true")
    List<HoraAula> findByTurmaId(@Param("turmaId") Integer id);
}
