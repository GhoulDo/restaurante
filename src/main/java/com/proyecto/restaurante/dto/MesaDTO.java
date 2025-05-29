package com.proyecto.restaurante.dto;

import com.proyecto.restaurante.model.Mesa;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MesaDTO {

    private Long id;

    @NotNull(message = "El número de mesa es obligatorio")
    private Integer numeroMesa;

    private Mesa.EstadoMesa estado;
}
