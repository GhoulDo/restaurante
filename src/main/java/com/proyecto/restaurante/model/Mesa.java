package com.proyecto.restaurante.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "mesas")
@Data
@EqualsAndHashCode(callSuper = false)
public class Mesa extends BaseEntity {

    @Column(name = "numero_mesa", nullable = false, unique = true)
    private Integer numeroMesa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMesa estado = EstadoMesa.LIBRE;

    public enum EstadoMesa {
        LIBRE, OCUPADA, RESERVADA
    }
}
