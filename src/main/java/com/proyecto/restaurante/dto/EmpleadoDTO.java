package com.proyecto.restaurante.dto;

import com.proyecto.restaurante.model.Empleado;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoDTO {

    private Long id;

    private String nombre;

    @NotNull(message = "El rol es obligatorio")
    private Empleado.RolEmpleado rol;

    private String telefono;

    @Email(message = "El formato del correo no es válido")
    private String correo;
}
