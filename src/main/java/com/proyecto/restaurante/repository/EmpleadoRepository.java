package com.proyecto.restaurante.repository;

import com.proyecto.restaurante.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    List<Empleado> findByRol(Empleado.RolEmpleado rol);

    Optional<Empleado> findByCorreo(String correo);

    List<Empleado> findByNombreContainingIgnoreCase(String nombre);
}
