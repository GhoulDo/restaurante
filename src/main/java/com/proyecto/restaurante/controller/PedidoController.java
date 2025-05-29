package com.proyecto.restaurante.controller;

import com.proyecto.restaurante.dto.DetallePedidoDTO;
import com.proyecto.restaurante.dto.PedidoDTO;
import com.proyecto.restaurante.model.Pedido;
import com.proyecto.restaurante.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> getAllPedidos() {
        List<PedidoDTO> pedidos = pedidoService.findAll();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> getPedidoById(@PathVariable Long id) {
        PedidoDTO pedido = pedidoService.findById(id);
        return ResponseEntity.ok(pedido);
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> createPedido(@Valid @RequestBody PedidoDTO pedidoDTO) {
        PedidoDTO nuevoPedido = pedidoService.save(pedidoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> updatePedido(@PathVariable Long id, @Valid @RequestBody PedidoDTO pedidoDTO) {
        PedidoDTO pedidoActualizado = pedidoService.update(id, pedidoDTO);
        return ResponseEntity.ok(pedidoActualizado);
    }

    @PostMapping("/{id}/detalles")
    public ResponseEntity<PedidoDTO> agregarDetalle(@PathVariable Long id,
            @Valid @RequestBody DetallePedidoDTO detalleDTO) {
        PedidoDTO pedidoActualizado = pedidoService.agregarDetalle(id, detalleDTO);
        return ResponseEntity.ok(pedidoActualizado);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PedidoDTO>> getPedidosByEstado(@PathVariable Pedido.EstadoPedido estado) {
        List<PedidoDTO> pedidos = pedidoService.findByEstado(estado);
        return ResponseEntity.ok(pedidos);
    }
}
