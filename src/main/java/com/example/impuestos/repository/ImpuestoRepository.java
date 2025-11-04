package com.example.impuestos.repository;

import com.example.impuestos.entity.Impuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ImpuestoRepository extends JpaRepository<Impuesto, Long> {

    List<Impuesto> findByFechaMovimiento(LocalDate fechaMovimiento);

    @Query("SELECT COUNT(i), SUM(i.valor) FROM Impuesto i WHERE i.tipoHorario = :tipoHorario")
    Object[] obtenerCantidadYValorPorHorario(@Param("tipoHorario") String tipoHorario);
}
