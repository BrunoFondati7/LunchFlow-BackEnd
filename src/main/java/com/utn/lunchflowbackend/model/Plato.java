package com.utn.lunchflowbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "plato")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plato")
    @JsonProperty("id_plato")
    private Long idPlato;

    @Column(name = "nombre_plato", nullable = false)
    private String nombrePlato;

    @Column(name = "descripcion_plato")
    private String descripcionPlato;

    @Column(name = "precio_plato")
    private Double precioPlato;

    @Column(name = "url_imagen")
    private String urlImagen;

    @Column(name = "activo_plato")
    private boolean activoPlato = true;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    // Al usar @Data, Lombok genera automáticamente:
    // getIdPlato(), getNombrePlato(), getDescripcionPlato(), etc.
}