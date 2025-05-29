package com.proyecto.restaurante.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String nombre;

    @NotNull(message = "El rol es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RolEmpleado rol;

    @Column(length = 20)
    private String telefono;

    @Email(message = "El formato del correo no es válido")
    @Column(length = 100)
    private String correo;

    public enum RolEmpleado {
        MESERO, COCINERO, CAJERO, ADMIN
    }
}
