package com.proyecto.restaurante.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventario")
@Data
@EqualsAndHashCode(callSuper = false)
public class Inventario extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false, unique = true)
    private Producto producto;

    @Column(name = "cantidad_stock", nullable = false)
    private Integer cantidadStock = 0;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @PrePersist
    @PreUpdate
    private void updateFechaActualizacion() {
        fechaActualizacion = LocalDateTime.now();
    }
}
