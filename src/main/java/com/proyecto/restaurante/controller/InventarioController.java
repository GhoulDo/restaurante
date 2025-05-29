package com.proyecto.restaurante.controller;

import com.proyecto.restaurante.dto.InventarioDTO;
import com.proyecto.restaurante.service.InventarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class InventarioController {

    private final InventarioService inventarioService;

    @GetMapping
    public ResponseEntity<List<InventarioDTO>> getAllInventario() {
        try {
            log.info("GET /api/inventario - Obteniendo todo el inventario");
            List<InventarioDTO> inventario = inventarioService.findAll();
            log.info("GET /api/inventario - Retornando {} registros de inventario", inventario.size());
            return ResponseEntity.ok(inventario);
        } catch (Exception e) {
            log.error("Error en GET /api/inventario: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventarioDTO> getInventarioById(@PathVariable Long id) {
        try {
            log.info("GET /api/inventario/{} - Obteniendo inventario por ID", id);
            InventarioDTO inventario = inventarioService.findById(id);
            return ResponseEntity.ok(inventario);
        } catch (Exception e) {
            log.error("Error en GET /api/inventario/{}: {}", id, e.getMessage());
            throw e;
        }
    }

    @GetMapping("/stock-bajo")
    public ResponseEntity<List<InventarioDTO>> getStockBajo(@RequestParam(defaultValue = "5") Integer minimo) {
        try {
            log.info("GET /api/inventario/stock-bajo?minimo={} - Obteniendo productos con stock bajo", minimo);
            List<InventarioDTO> stockBajo = inventarioService.findStockBajo(minimo);
            log.info("GET /api/inventario/stock-bajo - Encontrados {} productos con stock bajo", stockBajo.size());
            return ResponseEntity.ok(stockBajo);
        } catch (Exception e) {
            log.error("Error en GET /api/inventario/stock-bajo: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<InventarioDTO> getInventarioByProducto(@PathVariable Long productoId) {
        try {
            log.info("GET /api/inventario/producto/{} - Obteniendo inventario por producto", productoId);
            InventarioDTO inventario = inventarioService.findByProductoId(productoId);
            return ResponseEntity.ok(inventario);
        } catch (Exception e) {
            log.error("Error en GET /api/inventario/producto/{}: {}", productoId, e.getMessage());
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<InventarioDTO> createInventario(@Valid @RequestBody InventarioDTO inventarioDTO) {
        try {
            log.info("POST /api/inventario - Creando nuevo registro de inventario para producto: {}",
                    inventarioDTO.getProductoId());
            InventarioDTO nuevoInventario = inventarioService.save(inventarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoInventario);
        } catch (Exception e) {
            log.error("Error en POST /api/inventario: {}", e.getMessage());
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventarioDTO> updateInventario(@PathVariable Long id,
            @Valid @RequestBody InventarioDTO inventarioDTO) {
        try {
            log.info("PUT /api/inventario/{} - Actualizando inventario", id);
            InventarioDTO inventarioActualizado = inventarioService.update(id, inventarioDTO);
            return ResponseEntity.ok(inventarioActualizado);
        } catch (Exception e) {
            log.error("Error en PUT /api/inventario/{}: {}", id, e.getMessage());
            throw e;
        }
    }

    @PutMapping("/producto/{productoId}/stock")
    public ResponseEntity<InventarioDTO> actualizarStock(@PathVariable Long productoId,
            @RequestParam Integer cantidad) {
        try {
            log.info("PUT /api/inventario/producto/{}/stock?cantidad={} - Actualizando stock", productoId, cantidad);
            InventarioDTO inventarioActualizado = inventarioService.actualizarStock(productoId, cantidad);
            return ResponseEntity.ok(inventarioActualizado);
        } catch (Exception e) {
            log.error("Error en PUT /api/inventario/producto/{}/stock: {}", productoId, e.getMessage());
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventario(@PathVariable Long id) {
        try {
            log.info("DELETE /api/inventario/{} - Eliminando registro de inventario", id);
            inventarioService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error en DELETE /api/inventario/{}: {}", id, e.getMessage());
            throw e;
        }
    }
}
