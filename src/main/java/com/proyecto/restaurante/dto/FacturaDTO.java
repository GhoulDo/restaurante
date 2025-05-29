package com.proyecto.restaurante.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacturaDTO {

    private Long id;

    @NotNull(message = "El pedido es obligatorio")
    private Long pedidoId;

    private Integer numeroMesa;

    private LocalDateTime fecha;

    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "0.01", message = "El total debe ser mayor a 0")
    private BigDecimal total;

    private Long empleadoId;

    private String nombreEmpleado;
}
