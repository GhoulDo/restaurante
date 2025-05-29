package com.proyecto.restaurante.controller;

import com.proyecto.restaurante.dto.EmpleadoDTO;
import com.proyecto.restaurante.model.Empleado;
import com.proyecto.restaurante.service.EmpleadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<List<EmpleadoDTO>> getAllEmpleados() {
        try {
            log.info("GET /api/empleados - Obteniendo todos los empleados");
            List<EmpleadoDTO> empleados = empleadoService.findAll();
            log.info("GET /api/empleados - Retornando {} empleados", empleados.size());
            return ResponseEntity.ok(empleados);
        } catch (Exception e) {
            log.error("Error en GET /api/empleados: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> getEmpleadoById(@PathVariable Long id) {
        try {
            log.info("GET /api/empleados/{} - Obteniendo empleado por ID", id);
            EmpleadoDTO empleado = empleadoService.findById(id);
            return ResponseEntity.ok(empleado);
        } catch (Exception e) {
            log.error("Error en GET /api/empleados/{}: {}", id, e.getMessage());
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<EmpleadoDTO> createEmpleado(@Valid @RequestBody EmpleadoDTO empleadoDTO) {
        try {
            log.info("POST /api/empleados - Creando nuevo empleado: {}", empleadoDTO.getNombre());
            EmpleadoDTO nuevoEmpleado = empleadoService.save(empleadoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEmpleado);
        } catch (Exception e) {
            log.error("Error en POST /api/empleados: {}", e.getMessage());
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> updateEmpleado(@PathVariable Long id,
            @Valid @RequestBody EmpleadoDTO empleadoDTO) {
        try {
            log.info("PUT /api/empleados/{} - Actualizando empleado", id);
            EmpleadoDTO empleadoActualizado = empleadoService.update(id, empleadoDTO);
            return ResponseEntity.ok(empleadoActualizado);
        } catch (Exception e) {
            log.error("Error en PUT /api/empleados/{}: {}", id, e.getMessage());
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpleado(@PathVariable Long id) {
        try {
            log.info("DELETE /api/empleados/{} - Eliminando empleado", id);
            empleadoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error en DELETE /api/empleados/{}: {}", id, e.getMessage());
            throw e;
        }
    }

    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<EmpleadoDTO>> getEmpleadosByRol(@PathVariable Empleado.RolEmpleado rol) {
        try {
            log.info("GET /api/empleados/rol/{} - Obteniendo empleados por rol", rol);
            List<EmpleadoDTO> empleados = empleadoService.findByRol(rol);
            return ResponseEntity.ok(empleados);
        } catch (Exception e) {
            log.error("Error en GET /api/empleados/rol/{}: {}", rol, e.getMessage());
            throw e;
        }
    }
}
