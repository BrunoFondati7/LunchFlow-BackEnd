package com.utn.lunchflowbackend.service;

import com.utn.lunchflowbackend.dto.SeleccionRequest;
import com.utn.lunchflowbackend.model.*;
import com.utn.lunchflowbackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class SeleccionService {

    @Autowired
    private SeleccionRepository seleccionRepository;

    @Transactional
    public Seleccion guardarSeleccionCompleta(SeleccionRequest request) {
        // 1. Creamos la cabecera (Seleccion)
        Seleccion seleccion = new Seleccion();
        seleccion.setLegajoUser(request.getLegajoUser());
        seleccion.setIdMenu(request.getIdMenu());
        seleccion.setFechaRegistro(LocalDateTime.now());

        // Guardamos la cabecera primero para tener el ID
        Seleccion seleccionGuardada = seleccionRepository.save(seleccion);

        // 2. Creamos los detalles (los 5 platos)
        for (SeleccionRequest.DetalleSeleccionDTO dto : request.getDetalles()) {
            DetalleSeleccion detalle = new DetalleSeleccion();

            // Creamos el ID compuesto
            DetalleSeleccionId id = new DetalleSeleccionId();
            id.setIdSeleccion(seleccionGuardada.getIdSeleccion());
            id.setIdPlato(dto.getIdPlato());
            id.setDiaSemana(dto.getDiaSemana());

            detalle.setId(id);
            detalle.setSeleccion(seleccionGuardada);
            detalle.setCantidad(1); // Siempre 1 según el DER

            // Los detalles se guardan automáticamente por el CascadeType.ALL
            // que pusimos en la entidad Seleccion, pero si no, los agregaríamos a una lista.
            if (seleccionGuardada.getDetalles() == null) seleccionGuardada.setDetalles(new ArrayList<>());
            seleccionGuardada.getDetalles().add(detalle);
        }

        return seleccionRepository.save(seleccionGuardada);
    }
}