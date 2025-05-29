package com.proyecto.restaurante.controller;

import com.proyecto.restaurante.dto.InventarioDTO;
import com.proyecto.restaurante.service.InventarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class InventarioController {

    private final InventarioService inventarioService;

    @GetMapping
    public ResponseEntity<List<InventarioDTO>> getAllInventario() {
        List<InventarioDTO> inventario = inventarioService.findAll();
        return ResponseEntity.ok(inventario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventarioDTO> getInventarioById(@PathVariable Long id) {
        InventarioDTO inventario = inventarioService.findById(id);
        return ResponseEntity.ok(inventario);
    }

    @PostMapping
    public ResponseEntity<InventarioDTO> createInventario(@Valid @RequestBody InventarioDTO inventarioDTO) {
        InventarioDTO nuevoInventario = inventarioService.save(inventarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoInventario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventarioDTO> updateInventario(@PathVariable Long id,
            @Valid @RequestBody InventarioDTO inventarioDTO) {
        InventarioDTO inventarioActualizado = inventarioService.update(id, inventarioDTO);
        return ResponseEntity.ok(inventarioActualizado);
    }

    @PutMapping("/producto/{productoId}/stock")
    public ResponseEntity<InventarioDTO> actualizarStock(@PathVariable Long productoId,
            @RequestParam Integer cantidad) {
        InventarioDTO inventarioActualizado = inventarioService.actualizarStock(productoId, cantidad);
        return ResponseEntity.ok(inventarioActualizado);
    }

    @GetMapping("/stock-bajo")
    public ResponseEntity<List<InventarioDTO>> getStockBajo(@RequestParam(defaultValue = "5") Integer minimo) {
        List<InventarioDTO> stockBajo = inventarioService.findStockBajo(minimo);
        return ResponseEntity.ok(stockBajo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventario(@PathVariable Long id) {
        inventarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
