package com.utn.lunchflowbackend.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Embeddable
@Data
public class DetalleSeleccionId implements Serializable {
    private Integer idSeleccion;
    private Integer idPlato;
    private Integer diaSemana;
}