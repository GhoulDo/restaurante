package com.proyecto.restaurante.repository;

import com.proyecto.restaurante.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {

    List<Mesa> findByEstado(Mesa.EstadoMesa estado);

    Optional<Mesa> findByNumeroMesa(Integer numeroMesa);

    boolean existsByNumeroMesa(Integer numeroMesa);
}
