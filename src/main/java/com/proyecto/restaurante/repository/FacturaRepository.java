package com.proyecto.restaurante.repository;

import com.proyecto.restaurante.model.Empleado;
import com.proyecto.restaurante.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    List<Factura> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    List<Factura> findByEmpleado(Empleado empleado);

    @Query("SELECT SUM(f.total) FROM Factura f WHERE f.fecha BETWEEN :inicio AND :fin")
    BigDecimal calcularTotalVentasPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
}
