package com.proyecto.restaurante.controller;

import com.proyecto.restaurante.dto.ClienteDTO;
import com.proyecto.restaurante.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> getAllClientes() {
        List<ClienteDTO> clientes = clienteService.findAll();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Long id) {
        ClienteDTO cliente = clienteService.findById(id);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> createCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO nuevoCliente = clienteService.save(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> updateCliente(@PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO clienteActualizado = clienteService.update(id, clienteDTO);
        return ResponseEntity.ok(clienteActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/telefono/{telefono}")
    public ResponseEntity<ClienteDTO> getClienteByTelefono(@PathVariable String telefono) {
        ClienteDTO cliente = clienteService.findByTelefono(telefono);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ClienteDTO>> buscarClientesPorNombre(@RequestParam String nombre) {
        List<ClienteDTO> clientes = clienteService.findByNombre(nombre);
        return ResponseEntity.ok(clientes);
    }
}
