package com.proyecto.restaurante.dto;

import com.proyecto.restaurante.model.Mesa;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MesaDTO {

    private Long id;

    @NotNull(message = "El número de mesa es obligatorio")
    private Integer numeroMesa;

    private Mesa.EstadoMesa estado;
}
