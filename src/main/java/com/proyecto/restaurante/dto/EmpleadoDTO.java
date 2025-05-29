package com.proyecto.restaurante.dto;

import com.proyecto.restaurante.model.Empleado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmpleadoDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El rol es obligatorio")
    private Empleado.RolEmpleado rol;

    private String telefono;

    private String correo;
}
