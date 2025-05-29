package com.proyecto.restaurante.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Table(name = "mesas")
@Data
@EqualsAndHashCode(callSuper = false)
public class Mesa extends BaseEntity {

    @Column(name = "numero_mesa", nullable = false, unique = true)
    private Integer numeroMesa;

    @Column(nullable = false)
    private Integer capacidad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMesa estado = EstadoMesa.LIBRE;

    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pedido> pedidos;

    public enum EstadoMesa {
        LIBRE, OCUPADA, RESERVADA
    }
}
