package com.proyecto.restaurante.service;

import com.proyecto.restaurante.dto.DetallePedidoDTO;
import com.proyecto.restaurante.dto.PedidoDTO;
import com.proyecto.restaurante.model.*;
import com.proyecto.restaurante.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

        // Verificar que la mesa no tenga pedidos activos
        boolean tienepedidosActivos = pedidoRepository.existsByMesaAndEstadoIn(mesa,
                Arrays.asList(Pedido.EstadoPedido.PENDIENTE, Pedido.EstadoPedido.EN_PREPARACION));

        if (tienepedidosActivos) {
            throw new RuntimeException("La mesa ya tiene pedidos activos");
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

    public List<PedidoDTO> findByMesa(Long mesaId) {
        Mesa mesa = mesaRepository.findById(mesaId)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
        return pedidoRepository.findByMesa(mesa).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PedidoDTO agregarProducto(Long pedidoId, DetallePedidoDTO detallePedidoDTO) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        Producto producto = productoRepository.findById(detallePedidoDTO.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        DetallePedido detalle = new DetallePedido();
        detalle.setPedido(pedido);
        detalle.setProducto(producto);
        detalle.setCantidad(detallePedidoDTO.getCantidad());
        detalle.setPrecioUnitario(producto.getPrecio());
        // El método calcularSubtotal() se ejecuta automáticamente en @PrePersist

        detallePedidoRepository.save(detalle);

        // Actualizar el total del pedido
        pedido.setTotal(calcularTotalPedido(pedido));
        pedidoRepository.save(pedido);

        return convertToDTO(pedido);
    }

    public void delete(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new RuntimeException("Pedido no encontrado");
        }
        pedidoRepository.deleteById(id);
    }

    private BigDecimal calcularTotalPedido(Pedido pedido) {
        return detallePedidoRepository.findByPedido(pedido).stream()
                .map(DetallePedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private PedidoDTO convertToDTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setMesaId(pedido.getMesa().getId());
        dto.setNumeroMesa(pedido.getMesa().getNumeroMesa());

        if (pedido.getCliente() != null) {
            dto.setClienteId(pedido.getCliente().getId());
            dto.setNombreCliente(pedido.getCliente().getNombre());
        }

        dto.setEmpleadoId(pedido.getEmpleado().getId());
        dto.setNombreEmpleado(pedido.getEmpleado().getNombre());
        dto.setFecha(pedido.getFecha());
        dto.setEstado(pedido.getEstado());
        dto.setTotal(pedido.calcularTotal());

        if (pedido.getDetalles() != null) {
            dto.setDetalles(pedido.getDetalles().stream()
                    .map(this::convertDetallePedidoToDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    private DetallePedidoDTO convertDetallePedidoToDTO(DetallePedido detalle) {
        DetallePedidoDTO dto = new DetallePedidoDTO();
        dto.setId(detalle.getId());
        dto.setPedidoId(detalle.getPedido().getId());
        dto.setProductoId(detalle.getProducto().getId());
        dto.setNombreProducto(detalle.getProducto().getNombre());
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        dto.setSubtotal(detalle.getSubtotal());
        return dto;
    }
}
