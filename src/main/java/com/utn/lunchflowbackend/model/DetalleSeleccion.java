package com.utn.lunchflowbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference; // Importante
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "detalle_seleccion")
@Data
public class DetalleSeleccion {

    @EmbeddedId
    private DetalleSeleccionId id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "id_plato", referencedColumnName = "id_plato", insertable = false, updatable = false)
    })
    private Plato plato;

    @ManyToOne
    @MapsId("idSeleccion")
    @JoinColumn(name = "id_seleccion_detalle")
    @JsonBackReference // <--- ESTO corta la recursión infinita
    private Seleccion seleccion;

    @Column(name = "cantidad_detalle")
    private Integer cantidad;
}