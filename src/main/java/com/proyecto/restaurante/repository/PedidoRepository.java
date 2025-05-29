package com.proyecto.restaurante.repository;

import com.proyecto.restaurante.model.Mesa;
import com.proyecto.restaurante.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByEstado(Pedido.EstadoPedido estado);

    List<Pedido> findByMesaOrderByFechaDesc(Mesa mesa);

    @Query("SELECT p FROM Pedido p WHERE p.mesa = :mesa AND p.estado IN ('PENDIENTE', 'EN_PREPARACION')")
    Optional<Pedido> findPedidoActivoByMesa(@Param("mesa") Mesa mesa);

    @Query("SELECT p FROM Pedido p WHERE p.fecha BETWEEN :inicio AND :fin ORDER BY p.fecha DESC")
    List<Pedido> findByFechaBetween(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

    boolean existsByMesaAndEstadoIn(Mesa mesa, List<Pedido.EstadoPedido> estados);
}
