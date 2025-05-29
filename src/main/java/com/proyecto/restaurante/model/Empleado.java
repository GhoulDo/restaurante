package com.proyecto.restaurante.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "empleados")
@Data
@EqualsAndHashCode(callSuper = false)
public class Empleado extends BaseEntity {

    @Column(nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolEmpleado rol;

    private String telefono;

    private String correo;

    public enum RolEmpleado {
        ADMIN, CAJERO, MESERO, COCINERO
    }
}
