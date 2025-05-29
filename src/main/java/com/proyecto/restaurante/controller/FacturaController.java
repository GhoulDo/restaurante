package com.proyecto.restaurante.controller;

import com.proyecto.restaurante.dto.FacturaDTO;
import com.proyecto.restaurante.service.FacturaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class FacturaController {

    private final FacturaService facturaService;

    @GetMapping
    public ResponseEntity<List<FacturaDTO>> getAllFacturas() {
        try {
            log.info("GET /api/facturas - Obteniendo todas las facturas");
            List<FacturaDTO> facturas = facturaService.findAll();
            log.info("GET /api/facturas - Retornando {} facturas", facturas.size());
            return ResponseEntity.ok(facturas);
        } catch (Exception e) {
            log.error("Error en GET /api/facturas: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaDTO> getFacturaById(@PathVariable Long id) {
        try {
            log.info("GET /api/facturas/{} - Obteniendo factura por ID", id);
            FacturaDTO factura = facturaService.findById(id);
            return ResponseEntity.ok(factura);
        } catch (Exception e) {
            log.error("Error en GET /api/facturas/{}: {}", id, e.getMessage());
            throw e;
        }
    }

    @PostMapping("/pedido/{pedidoId}/empleado/{empleadoId}")
    public ResponseEntity<FacturaDTO> crearFactura(@PathVariable Long pedidoId, @PathVariable Long empleadoId) {
        try {
            log.info("POST /api/facturas/pedido/{}/empleado/{} - Creando factura", pedidoId, empleadoId);
            FacturaDTO nuevaFactura = facturaService.crearFacturaDesdePedido(pedidoId, empleadoId);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaFactura);
        } catch (Exception e) {
            log.error("Error en POST /api/facturas/pedido/{}/empleado/{}: {}", pedidoId, empleadoId, e.getMessage());
            throw e;
        }
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<FacturaDTO>> getFacturasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        try {
            log.info("GET /api/facturas/periodo - Obteniendo facturas desde {} hasta {}", inicio, fin);
            List<FacturaDTO> facturas = facturaService.findByFechaBetween(inicio, fin);
            log.info("GET /api/facturas/periodo - Retornando {} facturas", facturas.size());
            return ResponseEntity.ok(facturas);
        } catch (Exception e) {
            log.error("Error en GET /api/facturas/periodo: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/ventas-total")
    public ResponseEntity<BigDecimal> getTotalVentas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        try {
            log.info("GET /api/facturas/ventas-total - Calculando ventas desde {} hasta {}", inicio, fin);
            BigDecimal totalVentas = facturaService.calcularTotalVentas(inicio, fin);
            log.info("GET /api/facturas/ventas-total - Total de ventas: {}", totalVentas);
            return ResponseEntity.ok(totalVentas);
        } catch (Exception e) {
            log.error("Error en GET /api/facturas/ventas-total: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/empleado/{empleadoId}")
    public ResponseEntity<List<FacturaDTO>> getFacturasByEmpleado(@PathVariable Long empleadoId) {
        try {
            log.info("GET /api/facturas/empleado/{} - Obteniendo facturas por empleado", empleadoId);
            List<FacturaDTO> facturas = facturaService.findByEmpleadoId(empleadoId);
            log.info("GET /api/facturas/empleado/{} - Retornando {} facturas", empleadoId, facturas.size());
            return ResponseEntity.ok(facturas);
        } catch (Exception e) {
            log.error("Error en GET /api/facturas/empleado/{}: {}", empleadoId, e.getMessage());
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFactura(@PathVariable Long id) {
        try {
            log.info("DELETE /api/facturas/{} - Eliminando factura", id);
            facturaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error en DELETE /api/facturas/{}: {}", id, e.getMessage());
            throw e;
        }
    }
}
