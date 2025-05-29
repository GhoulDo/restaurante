package com.proyecto.restaurante.controller;

import com.proyecto.restaurante.dto.MesaDTO;
import com.proyecto.restaurante.model.Mesa;
import com.proyecto.restaurante.service.MesaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MesaController {

    private final MesaService mesaService;

    @GetMapping
    public ResponseEntity<List<MesaDTO>> getAllMesas() {
        List<MesaDTO> mesas = mesaService.findAll();
        return ResponseEntity.ok(mesas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MesaDTO> getMesaById(@PathVariable Long id) {
        MesaDTO mesa = mesaService.findById(id);
        return ResponseEntity.ok(mesa);
    }

    @PostMapping
    public ResponseEntity<MesaDTO> createMesa(@Valid @RequestBody MesaDTO mesaDTO) {
        MesaDTO nuevaMesa = mesaService.save(mesaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMesa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MesaDTO> updateMesa(@PathVariable Long id, @Valid @RequestBody MesaDTO mesaDTO) {
        MesaDTO mesaActualizada = mesaService.update(id, mesaDTO);
        return ResponseEntity.ok(mesaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMesa(@PathVariable Long id) {
        mesaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<MesaDTO>> getMesasByEstado(@PathVariable Mesa.EstadoMesa estado) {
        List<MesaDTO> mesas = mesaService.findByEstado(estado);
        return ResponseEntity.ok(mesas);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<MesaDTO> cambiarEstadoMesa(@PathVariable Long id, @RequestParam Mesa.EstadoMesa estado) {
        MesaDTO mesaActualizada = mesaService.cambiarEstado(id, estado);
        return ResponseEntity.ok(mesaActualizada);
    }
}
