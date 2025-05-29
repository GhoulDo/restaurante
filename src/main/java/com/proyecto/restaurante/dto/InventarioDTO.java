package com.proyecto.restaurante.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InventarioDTO {

    private Long id;

    @NotNull(message = "El producto es obligatorio")
    private Long productoId;

    private String nombreProducto;

    @NotNull(message = "La cantidad de stock es obligatoria")
    @Min(value = 0, message = "La cantidad de stock no puede ser negativa")
    private Integer cantidadStock;

    private LocalDateTime fechaActualizacion;
}
