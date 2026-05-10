package com.utn.lunchflowbackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "plato")
@Data
public class Plato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlato;

    @Column(nullable = false)
    private String nombrePlato;

    private String descripcionPlato;
    private Double precioPlato;

    private boolean activoPlato = true;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;
}