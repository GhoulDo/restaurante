package com.proyecto.restaurante.repository;

import com.proyecto.restaurante.model.Inventario;
import com.proyecto.restaurante.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    @Query("SELECT i FROM Inventario i WHERE i.cantidadStock <= :minimo")
    List<Inventario> findByStockBajo(@Param("minimo") Integer minimo);

    @Query("SELECT i FROM Inventario i WHERE i.cantidadStock <= :minimo")
    List<Inventario> findProductosConStockBajo(@Param("minimo") Integer minimo);

    Optional<Inventario> findByProducto(Producto producto);

    @Query("SELECT i FROM Inventario i WHERE i.producto.id = :productoId")
    Optional<Inventario> findByProductoId(@Param("productoId") Long productoId);
}
