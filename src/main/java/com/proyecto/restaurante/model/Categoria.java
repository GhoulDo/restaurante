package com.proyecto.restaurante.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Table(name = "categorias")
@Data
@EqualsAndHashCode(callSuper = false)
public class Categoria extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String nombre;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Producto> productos;

    public int getCantidadProductos() {
        return productos != null ? productos.size() : 0;
    }
}
