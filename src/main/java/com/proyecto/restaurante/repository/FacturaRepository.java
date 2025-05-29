package com.proyecto.restaurante.repository;

import com.proyecto.restaurante.model.Factura;
import com.proyecto.restaurante.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    Optional<Factura> findByPedido(Pedido pedido);

    @Query("SELECT f FROM Factura f WHERE f.fecha BETWEEN :inicio AND :fin ORDER BY f.fecha DESC")
    List<Factura> findByFechaBetween(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

    @Query("SELECT SUM(f.total) FROM Factura f WHERE f.fecha BETWEEN :inicio AND :fin")
    Double calcularTotalVentasPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
}
