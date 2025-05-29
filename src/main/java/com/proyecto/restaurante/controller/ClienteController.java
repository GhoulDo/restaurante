package com.proyecto.restaurante.controller;

import com.proyecto.restaurante.dto.ClienteDTO;
import com.proyecto.restaurante.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> getAllClientes() {
        try {
            log.info("GET /api/clientes - Obteniendo todos los clientes");
            List<ClienteDTO> clientes = clienteService.findAll();
            log.info("GET /api/clientes - Retornando {} clientes", clientes.size());
            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            log.error("Error en GET /api/clientes: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Long id) {
        try {
            log.info("GET /api/clientes/{} - Obteniendo cliente por ID", id);
            ClienteDTO cliente = clienteService.findById(id);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            log.error("Error en GET /api/clientes/{}: {}", id, e.getMessage());
            throw e;
        }
    }

    @GetMapping("/telefono/{telefono}")
    public ResponseEntity<ClienteDTO> getClienteByTelefono(@PathVariable String telefono) {
        try {
            log.info("GET /api/clientes/telefono/{} - Buscando cliente por teléfono", telefono);
            ClienteDTO cliente = clienteService.findByTelefono(telefono);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            log.error("Error en GET /api/clientes/telefono/{}: {}", telefono, e.getMessage());
            throw e;
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ClienteDTO>> buscarClientesPorNombre(@RequestParam String nombre) {
        try {
            log.info("GET /api/clientes/buscar?nombre={} - Buscando clientes por nombre", nombre);
            List<ClienteDTO> clientes = clienteService.findByNombreContaining(nombre);
            log.info("GET /api/clientes/buscar - Encontrados {} clientes", clientes.size());
            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            log.error("Error en GET /api/clientes/buscar: {}", e.getMessage());
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> createCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        try {
            log.info("POST /api/clientes - Creando nuevo cliente: {}", clienteDTO.getNombre());
            ClienteDTO nuevoCliente = clienteService.save(clienteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
        } catch (Exception e) {
            log.error("Error en POST /api/clientes: {}", e.getMessage());
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> updateCliente(@PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO) {
        try {
            log.info("PUT /api/clientes/{} - Actualizando cliente", id);
            ClienteDTO clienteActualizado = clienteService.update(id, clienteDTO);
            return ResponseEntity.ok(clienteActualizado);
        } catch (Exception e) {
            log.error("Error en PUT /api/clientes/{}: {}", id, e.getMessage());
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        try {
            log.info("DELETE /api/clientes/{} - Eliminando cliente", id);
            clienteService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error en DELETE /api/clientes/{}: {}", id, e.getMessage());
            throw e;
        }
    }
}
