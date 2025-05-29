package com.proyecto.restaurante.repository;

import com.proyecto.restaurante.model.Categoria;
import com.proyecto.restaurante.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByCategoria(Categoria categoria);

    @Query("SELECT p FROM Producto p WHERE p.nombre LIKE %:nombre%")
    List<Producto> findByNombreContaining(@Param("nombre") String nombre);

    @Query("SELECT p FROM Producto p LEFT JOIN p.inventario i WHERE i.cantidadStock > 0 OR i.cantidadStock IS NULL")
    List<Producto> findProductosDisponibles();
}
