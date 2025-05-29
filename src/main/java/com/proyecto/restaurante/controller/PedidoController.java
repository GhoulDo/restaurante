package com.proyecto.restaurante.controller;

import com.proyecto.restaurante.dto.DetallePedidoDTO;
import com.proyecto.restaurante.dto.PedidoDTO;
import com.proyecto.restaurante.model.Pedido;
import com.proyecto.restaurante.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> getAllPedidos() {
        try {
            log.info("GET /api/pedidos - Obteniendo todos los pedidos");
            List<PedidoDTO> pedidos = pedidoService.findAll();
            log.info("GET /api/pedidos - Retornando {} pedidos", pedidos.size());
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            log.error("Error en GET /api/pedidos: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> getPedidoById(@PathVariable Long id) {
        try {
            log.info("GET /api/pedidos/{} - Obteniendo pedido por ID", id);
            PedidoDTO pedido = pedidoService.findById(id);
            return ResponseEntity.ok(pedido);
        } catch (Exception e) {
            log.error("Error en GET /api/pedidos/{}: {}", id, e.getMessage());
            throw e;
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PedidoDTO>> getPedidosByEstado(@PathVariable Pedido.EstadoPedido estado) {
        try {
            log.info("GET /api/pedidos/estado/{} - Obteniendo pedidos por estado", estado);
            List<PedidoDTO> pedidos = pedidoService.findByEstado(estado);
            log.info("GET /api/pedidos/estado/{} - Retornando {} pedidos", estado, pedidos.size());
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            log.error("Error en GET /api/pedidos/estado/{}: {}", estado, e.getMessage());
            throw e;
        }
    }

    @GetMapping("/mesa/{mesaId}")
    public ResponseEntity<List<PedidoDTO>> getPedidosByMesa(@PathVariable Long mesaId) {
        try {
            log.info("GET /api/pedidos/mesa/{} - Obteniendo pedidos por mesa", mesaId);
            List<PedidoDTO> pedidos = pedidoService.findByMesa(mesaId);
            log.info("GET /api/pedidos/mesa/{} - Retornando {} pedidos", mesaId, pedidos.size());
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            log.error("Error en GET /api/pedidos/mesa/{}: {}", mesaId, e.getMessage());
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> createPedido(@Valid @RequestBody PedidoDTO pedidoDTO) {
        try {
            log.info("POST /api/pedidos - Creando nuevo pedido para mesa: {}", pedidoDTO.getMesaId());
            PedidoDTO nuevoPedido = pedidoService.save(pedidoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
        } catch (Exception e) {
            log.error("Error en POST /api/pedidos: {}", e.getMessage());
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> updatePedido(@PathVariable Long id, @Valid @RequestBody PedidoDTO pedidoDTO) {
        try {
            log.info("PUT /api/pedidos/{} - Actualizando pedido", id);
            PedidoDTO pedidoActualizado = pedidoService.update(id, pedidoDTO);
            return ResponseEntity.ok(pedidoActualizado);
        } catch (Exception e) {
            log.error("Error en PUT /api/pedidos/{}: {}", id, e.getMessage());
            throw e;
        }
    }

    @PostMapping("/{id}/detalles")
    public ResponseEntity<PedidoDTO> agregarProductoAPedido(@PathVariable Long id,
            @Valid @RequestBody DetallePedidoDTO detallePedidoDTO) {
        try {
            log.info("POST /api/pedidos/{}/detalles - Agregando producto al pedido", id);
            PedidoDTO pedidoActualizado = pedidoService.agregarProducto(id, detallePedidoDTO);
            return ResponseEntity.ok(pedidoActualizado);
        } catch (Exception e) {
            log.error("Error en POST /api/pedidos/{}/detalles: {}", id, e.getMessage());
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        try {
            log.info("DELETE /api/pedidos/{} - Eliminando pedido", id);
            pedidoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error en DELETE /api/pedidos/{}: {}", id, e.getMessage());
            throw e;
        }
    }
}
