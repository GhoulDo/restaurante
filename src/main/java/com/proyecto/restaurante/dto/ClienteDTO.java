package com.proyecto.restaurante.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private Long id;

    private String nombre;

    private String telefono;

    @Email(message = "El formato del correo no es válido")
    private String correo;
}
