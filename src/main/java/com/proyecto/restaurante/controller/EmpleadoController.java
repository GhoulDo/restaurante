package com.proyecto.restaurante.controller;

import com.proyecto.restaurante.dto.EmpleadoDTO;
import com.proyecto.restaurante.model.Empleado;
import com.proyecto.restaurante.service.EmpleadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<List<EmpleadoDTO>> getAllEmpleados() {
        List<EmpleadoDTO> empleados = empleadoService.findAll();
        return ResponseEntity.ok(empleados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> getEmpleadoById(@PathVariable Long id) {
        EmpleadoDTO empleado = empleadoService.findById(id);
        return ResponseEntity.ok(empleado);
    }

    @PostMapping
    public ResponseEntity<EmpleadoDTO> createEmpleado(@Valid @RequestBody EmpleadoDTO empleadoDTO) {
        EmpleadoDTO nuevoEmpleado = empleadoService.save(empleadoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEmpleado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> updateEmpleado(@PathVariable Long id,
            @Valid @RequestBody EmpleadoDTO empleadoDTO) {
        EmpleadoDTO empleadoActualizado = empleadoService.update(id, empleadoDTO);
        return ResponseEntity.ok(empleadoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpleado(@PathVariable Long id) {
        empleadoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<EmpleadoDTO>> getEmpleadosByRol(@PathVariable Empleado.RolEmpleado rol) {
        List<EmpleadoDTO> empleados = empleadoService.findByRol(rol);
        return ResponseEntity.ok(empleados);
    }
}
