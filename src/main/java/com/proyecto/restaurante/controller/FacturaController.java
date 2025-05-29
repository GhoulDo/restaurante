package com.proyecto.restaurante.controller;

import com.proyecto.restaurante.dto.FacturaDTO;
import com.proyecto.restaurante.service.FacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FacturaController {

    private final FacturaService facturaService;

    @GetMapping
    public ResponseEntity<List<FacturaDTO>> getAllFacturas() {
        List<FacturaDTO> facturas = facturaService.findAll();
        return ResponseEntity.ok(facturas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaDTO> getFacturaById(@PathVariable Long id) {
        FacturaDTO factura = facturaService.findById(id);
        return ResponseEntity.ok(factura);
    }

    @PostMapping("/pedido/{pedidoId}/empleado/{empleadoId}")
    public ResponseEntity<FacturaDTO> crearFactura(@PathVariable Long pedidoId, @PathVariable Long empleadoId) {
        FacturaDTO nuevaFactura = facturaService.crearFacturaPorPedido(pedidoId, empleadoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaFactura);
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<FacturaDTO>> getFacturasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        List<FacturaDTO> facturas = facturaService.findByFechaBetween(inicio, fin);
        return ResponseEntity.ok(facturas);
    }

    @GetMapping("/ventas-total")
    public ResponseEntity<Double> getTotalVentasPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        Double total = facturaService.calcularTotalVentasPeriodo(inicio, fin);
        return ResponseEntity.ok(total);
    }
}
