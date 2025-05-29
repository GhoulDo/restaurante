package com.proyecto.restaurante.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Table(name = "clientes")
@Data
@EqualsAndHashCode(callSuper = false)
public class Cliente extends BaseEntity {

    @Column(nullable = false)
    private String nombre;

    private String telefono;

    private String correo;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pedido> pedidos;
}
