package com.utn.lunchflowbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "plato")
@Data
public class Plato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id_plato") // Agregá esta anotación de Jackson
    private Long idPlato;

    @Column(nullable = false)
    private String nombrePlato;

    private String descripcionPlato;
    private Double precioPlato;

    // Este es el campo nuevo para el link de la imagen (ej: de Google o un servidor)
    private String urlImagen;

    private boolean activoPlato = true;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;
}