package com.utn.lunchflowbackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "detalle_seleccion")
@Data
public class DetalleSeleccion {

    @EmbeddedId
    private DetalleSeleccionId id;

    @ManyToOne
    @MapsId("idSeleccion")
    @JoinColumn(name = "id_seleccion_detalle")
    private Seleccion seleccion;

    @Column(name = "cantidad_detalle")
    private Integer cantidad;

    // Aquí podrías agregar la relación ManyToOne con Plato si quisieras
    // pero para seguir el DER estrictamente usamos el ID en la PK compuesta.
}