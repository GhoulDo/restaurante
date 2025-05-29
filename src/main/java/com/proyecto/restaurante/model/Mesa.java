package com.proyecto.restaurante.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mesas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El número de mesa es obligatorio")
    @Column(name = "numero_mesa", nullable = false, unique = true)
    private Integer numeroMesa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMesa estado = EstadoMesa.LIBRE;

    public enum EstadoMesa {
        LIBRE, OCUPADA, RESERVADA
    }
}
