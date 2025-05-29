package com.proyecto.restaurante.repository;

import com.proyecto.restaurante.model.Mesa;
import com.proyecto.restaurante.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByEstado(Pedido.EstadoPedido estado);

    List<Pedido> findByMesa(Mesa mesa);

    boolean existsByMesaAndEstadoIn(Mesa mesa, List<Pedido.EstadoPedido> estados);
}
