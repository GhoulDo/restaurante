package com.proyecto.restaurante.dto;

import com.proyecto.restaurante.model.Mesa;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MesaDTO {

    private Long id;

    @NotNull(message = "El número de mesa es obligatorio")
    @Min(value = 1, message = "El número de mesa debe ser mayor a 0")
    private Integer numeroMesa;

    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad debe ser mayor a 0")
    private Integer capacidad;

    private Mesa.EstadoMesa estado = Mesa.EstadoMesa.LIBRE;
}
