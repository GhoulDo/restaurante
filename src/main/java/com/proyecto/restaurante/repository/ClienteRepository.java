package com.proyecto.restaurante.repository;

import com.proyecto.restaurante.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByTelefono(String telefono);

    Optional<Cliente> findByCorreo(String correo);

    List<Cliente> findByNombreContainingIgnoreCase(String nombre);
}
