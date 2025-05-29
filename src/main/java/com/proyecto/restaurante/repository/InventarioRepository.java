package com.proyecto.restaurante.repository;

import com.proyecto.restaurante.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    Optional<Inventario> findByProductoId(Long productoId);

    List<Inventario> findByCantidadStockLessThan(Integer cantidad);

    boolean existsByProductoId(Long productoId);
}
