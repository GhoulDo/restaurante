package com.proyecto.restaurante.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
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
        // Verificación más robusta para evitar NullPointerException
        if (productos == null) {
            return 0;
        }
        return productos.size();
    }

    // Método para inicializar la lista si es null
    public List<Producto> getProductos() {
        if (productos == null) {
            productos = new ArrayList<>();
        }
        return productos;
    }
}
