package com.proyecto.restaurante.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "clientes")
@Data
@EqualsAndHashCode(callSuper = false)
public class Cliente extends BaseEntity {

    @Column(nullable = false)
    private String nombre;

    @Column(unique = true)
    private String telefono;

    private String correo;
}
