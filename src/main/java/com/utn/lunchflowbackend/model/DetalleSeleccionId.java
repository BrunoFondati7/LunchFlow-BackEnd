package com.utn.lunchflowbackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Embeddable
@Data
public class DetalleSeleccionId implements Serializable {

    @Column(name = "id_seleccion_detalle") // Coincide con el @JoinColumn de Seleccion
    private Integer idSeleccion;

    @Column(name = "id_plato") // Coincide con el @JoinColumn de Plato
    private Integer idPlato;

    @Column(name = "dia_semana")
    private Integer diaSemana;
}