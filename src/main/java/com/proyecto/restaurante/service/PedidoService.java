package com.proyecto.restaurante.service;

import com.proyecto.restaurante.dto.DetallePedidoDTO;
import com.proyecto.restaurante.dto.PedidoDTO;
import com.proyecto.restaurante.model.*;
import com.proyecto.restaurante.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final MesaRepository mesaRepository;
    private final ProductoRepository productoRepository;
    private final DetallePedidoRepository detallePedidoRepository;
    private final ClienteRepository clienteRepository;
    private final EmpleadoRepository empleadoRepository;

    public List<PedidoDTO> findAll() {
        return pedidoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PedidoDTO findById(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        return convertToDTO(pedido);
    }

    public PedidoDTO save(PedidoDTO pedidoDTO) {
        Mesa mesa = mesaRepository.findById(pedidoDTO.getMesaId())
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));

        // Validar que la mesa no tenga un pedido activo
        if (pedidoRepository.existsByMesaAndEstadoIn(mesa,
                Arrays.asList(Pedido.EstadoPedido.PENDIENTE, Pedido.EstadoPedido.EN_PREPARACION))) {
            throw new RuntimeException("La mesa ya tiene un pedido activo");
        }

        Pedido pedido = new Pedido();
        pedido.setMesa(mesa);
        pedido.setEstado(Pedido.EstadoPedido.PENDIENTE);

        // Agregar cliente si se proporciona
        if (pedidoDTO.getClienteId() != null) {
            Cliente cliente = clienteRepository.findById(pedidoDTO.getClienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
            pedido.setCliente(cliente);
        }

        // Agregar empleado si se proporciona
        if (pedidoDTO.getEmpleadoId() != null) {
            Empleado empleado = empleadoRepository.findById(pedidoDTO.getEmpleadoId())
                    .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
            pedido.setEmpleado(empleado);
        }

        Pedido savedPedido = pedidoRepository.save(pedido);

        // Cambiar estado de la mesa a ocupada
        mesa.setEstado(Mesa.EstadoMesa.OCUPADA);
        mesaRepository.save(mesa);

        return convertToDTO(savedPedido);
    }

    public PedidoDTO update(Long id, PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        if (pedido.getEstado() == Pedido.EstadoPedido.SERVIDO ||
                pedido.getEstado() == Pedido.EstadoPedido.CANCELADO) {
            throw new RuntimeException("No se puede modificar un pedido servido o cancelado");
        }

        pedido.setEstado(pedidoDTO.getEstado());
        Pedido updatedPedido = pedidoRepository.save(pedido);

        // Si el pedido se sirve o cancela, liberar la mesa
        if (pedidoDTO.getEstado() == Pedido.EstadoPedido.SERVIDO ||
                pedidoDTO.getEstado() == Pedido.EstadoPedido.CANCELADO) {
            Mesa mesa = pedido.getMesa();
            mesa.setEstado(Mesa.EstadoMesa.LIBRE);
            mesaRepository.save(mesa);
        }

        return convertToDTO(updatedPedido);
    }

    public PedidoDTO agregarDetalle(Long pedidoId, DetallePedidoDTO detalleDTO) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        if (pedido.getEstado() == Pedido.EstadoPedido.SERVIDO ||
                pedido.getEstado() == Pedido.EstadoPedido.CANCELADO) {
            throw new RuntimeException("No se puede modificar un pedido servido o cancelado");
        }

        Producto producto = productoRepository.findById(detalleDTO.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        DetallePedido detalle = new DetallePedido();
        detalle.setPedido(pedido);
        detalle.setProducto(producto);
        detalle.setCantidad(detalleDTO.getCantidad());
        detalle.calcularSubtotal();

        detallePedidoRepository.save(detalle);

        // Recalcular total del pedido
        pedidoRepository.save(pedido);

        return convertToDTO(pedido);
    }

    public List<PedidoDTO> findByEstado(Pedido.EstadoPedido estado) {
        return pedidoRepository.findByEstado(estado).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private PedidoDTO convertToDTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setMesaId(pedido.getMesa().getId());
        dto.setNumeroMesa(pedido.getMesa().getNumeroMesa());
        dto.setFecha(pedido.getFecha());
        dto.setEstado(pedido.getEstado());
        dto.setTotal(pedido.calcularTotal());

        if (pedido.getCliente() != null) {
            dto.setClienteId(pedido.getCliente().getId());
            dto.setNombreCliente(pedido.getCliente().getNombre());
        }

        if (pedido.getEmpleado() != null) {
            dto.setEmpleadoId(pedido.getEmpleado().getId());
            dto.setNombreEmpleado(pedido.getEmpleado().getNombre());
        }

        List<DetallePedidoDTO> detallesDTO = pedido.getDetalles().stream()
                .map(this::convertDetalleToDTO)
                .collect(Collectors.toList());
        dto.setDetalles(detallesDTO);

        return dto;
    }

    private DetallePedidoDTO convertDetalleToDTO(DetallePedido detalle) {
        DetallePedidoDTO dto = new DetallePedidoDTO();
        dto.setId(detalle.getId());
        dto.setPedidoId(detalle.getPedido().getId());
        dto.setProductoId(detalle.getProducto().getId());
        dto.setNombreProducto(detalle.getProducto().getNombre());
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getProducto().getPrecio());
        dto.setSubtotal(detalle.getSubtotal());
        return dto;
    }
}
