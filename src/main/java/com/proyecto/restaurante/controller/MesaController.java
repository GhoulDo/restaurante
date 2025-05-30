package com.proyecto.restaurante.controller;

import com.proyecto.restaurante.dto.MesaDTO;
import com.proyecto.restaurante.model.Mesa;
import com.proyecto.restaurante.service.MesaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Validated
@Slf4j
public class MesaController {

    private final MesaService mesaService;

    @GetMapping
    public ResponseEntity<List<MesaDTO>> getAllMesas() {
        try {
            log.info("GET /api/mesas - Obteniendo todas las mesas");
            List<MesaDTO> mesas = mesaService.findAll();
            log.info("GET /api/mesas - Retornando {} mesas", mesas.size());
            return ResponseEntity.ok(mesas);
        } catch (Exception e) {
            log.error("Error en GET /api/mesas: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MesaDTO> getMesaById(@PathVariable Long id) {
        try {
            log.info("GET /api/mesas/{} - Obteniendo mesa por ID", id);
            MesaDTO mesa = mesaService.findById(id);
            return ResponseEntity.ok(mesa);
        } catch (Exception e) {
            log.error("Error en GET /api/mesas/{}: {}", id, e.getMessage());
            throw e;
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<MesaDTO>> getMesasByEstado(@PathVariable Mesa.EstadoMesa estado) {
        try {
            log.info("GET /api/mesas/estado/{} - Obteniendo mesas por estado", estado);
            List<MesaDTO> mesas = mesaService.findByEstado(estado);
            return ResponseEntity.ok(mesas);
        } catch (Exception e) {
            log.error("Error en GET /api/mesas/estado/{}: {}", estado, e.getMessage());
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<MesaDTO> createMesa(@Valid @RequestBody MesaDTO mesaDTO) {
        try {
            log.info("POST /api/mesas - Creando mesa número: {}", mesaDTO.getNumeroMesa());
            MesaDTO nuevaMesa = mesaService.save(mesaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMesa);
        } catch (Exception e) {
            log.error("Error en POST /api/mesas: {}", e.getMessage());
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MesaDTO> updateMesa(@PathVariable Long id, @Valid @RequestBody MesaDTO mesaDTO) {
        try {
            log.info("PUT /api/mesas/{} - Actualizando mesa", id);
            MesaDTO mesaActualizada = mesaService.update(id, mesaDTO);
            return ResponseEntity.ok(mesaActualizada);
        } catch (Exception e) {
            log.error("Error en PUT /api/mesas/{}: {}", id, e.getMessage());
            throw e;
        }
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<MesaDTO> cambiarEstadoMesa(@PathVariable Long id, @RequestParam Mesa.EstadoMesa estado) {
        try {
            log.info("PATCH /api/mesas/{}/estado?estado={} - Cambiando estado", id, estado);
            MesaDTO mesaActualizada = mesaService.cambiarEstado(id, estado);
            return ResponseEntity.ok(mesaActualizada);
        } catch (Exception e) {
            log.error("Error en PATCH /api/mesas/{}/estado: {}", id, e.getMessage());
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMesa(@PathVariable Long id) {
        try {
            log.info("DELETE /api/mesas/{} - Eliminando mesa", id);
            mesaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error en DELETE /api/mesas/{}: {}", id, e.getMessage());
            throw e;
        }
    }
}
