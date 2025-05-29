package com.proyecto.restaurante.dto;

import com.proyecto.restaurante.model.Pedido;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PedidoDTO {

    private Long id;

    @NotNull(message = "La mesa es obligatoria")
    private Long mesaId;

    private Integer numeroMesa;

    private Long clienteId;

    private String nombreCliente;

    @NotNull(message = "El empleado es obligatorio")
    private Long empleadoId;

    private String nombreEmpleado;

    private LocalDateTime fecha;

    private Pedido.EstadoPedido estado;

    private BigDecimal total;

    private List<DetallePedidoDTO> detalles;
}
