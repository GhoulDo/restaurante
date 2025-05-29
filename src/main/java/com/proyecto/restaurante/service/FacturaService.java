package com.proyecto.restaurante.service;

import com.proyecto.restaurante.dto.FacturaDTO;
import com.proyecto.restaurante.model.Empleado;
import com.proyecto.restaurante.model.Factura;
import com.proyecto.restaurante.model.Pedido;
import com.proyecto.restaurante.repository.EmpleadoRepository;
import com.proyecto.restaurante.repository.FacturaRepository;
import com.proyecto.restaurante.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final PedidoRepository pedidoRepository;
    private final EmpleadoRepository empleadoRepository;

    public List<FacturaDTO> findAll() {
        return facturaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public FacturaDTO findById(Long id) {
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));
        return convertToDTO(factura);
    }

    public List<FacturaDTO> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin) {
        return facturaRepository.findByFechaBetween(inicio, fin).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public FacturaDTO crearFacturaDesdePedido(Long pedidoId, Long empleadoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        if (!pedido.getEstado().equals(Pedido.EstadoPedido.SERVIDO)) {
            throw new RuntimeException("Solo se pueden facturar pedidos servidos");
        }

        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        Factura factura = new Factura();
        factura.setPedido(pedido);
        factura.setEmpleado(empleado);
        factura.setFecha(LocalDateTime.now());
        factura.setTotal(pedido.getTotal());

        Factura savedFactura = facturaRepository.save(factura);
        return convertToDTO(savedFactura);
    }

    public BigDecimal calcularTotalVentas(LocalDateTime inicio, LocalDateTime fin) {
        BigDecimal total = facturaRepository.calcularTotalVentasPeriodo(inicio, fin);
        return total != null ? total : BigDecimal.ZERO;
    }

    public List<FacturaDTO> findByEmpleadoId(Long empleadoId) {
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        return facturaRepository.findByEmpleado(empleado).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        if (!facturaRepository.existsById(id)) {
            throw new RuntimeException("Factura no encontrada");
        }
        facturaRepository.deleteById(id);
    }

    private FacturaDTO convertToDTO(Factura factura) {
        FacturaDTO dto = new FacturaDTO();
        dto.setId(factura.getId());
        dto.setPedidoId(factura.getPedido().getId());
        dto.setNumeroMesa(factura.getPedido().getMesa().getNumeroMesa());
        dto.setFecha(factura.getFecha());
        dto.setTotal(factura.getTotal());
        dto.setEmpleadoId(factura.getEmpleado().getId());
        dto.setNombreEmpleado(factura.getEmpleado().getNombre());
        return dto;
    }
}
