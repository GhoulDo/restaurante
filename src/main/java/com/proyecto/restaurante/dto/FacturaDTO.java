package com.proyecto.restaurante.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FacturaDTO {

    private Long id;

    private Long pedidoId;

    private Integer numeroMesa;

    private LocalDateTime fecha;

    private BigDecimal total;

    private Long empleadoId;

    private String nombreEmpleado;
}
