package com.utn.lunchflowbackend.service;

import com.utn.lunchflowbackend.dto.SeleccionRequest;
import com.utn.lunchflowbackend.model.*;
import com.utn.lunchflowbackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeleccionService {

    @Autowired
    private SeleccionRepository seleccionRepository;

    @Transactional
    public Seleccion guardarSeleccionCompleta(SeleccionRequest request) {
        // 1. Creamos la cabecera (Seleccion)
        Seleccion seleccion = new Seleccion();

        // CORRECCIÓN: Usamos el setter que genera Lombok para 'legajoCliente'
        seleccion.setLegajoCliente(request.getLegajoUser());

        seleccion.setIdMenu(request.getIdMenu());
        seleccion.setFechaRegistro(LocalDateTime.now());

        // Guardamos la cabecera primero para tener el ID generado
        Seleccion seleccionGuardada = seleccionRepository.save(seleccion);

        // 2. Creamos los detalles (los platos seleccionados)
        if (request.getDetalles() != null) {
            for (SeleccionRequest.DetalleSeleccionDTO dto : request.getDetalles()) {
                DetalleSeleccion detalle = new DetalleSeleccion();

                // Creamos el ID compuesto
                DetalleSeleccionId id = new DetalleSeleccionId();
                id.setIdSeleccion(seleccionGuardada.getIdSeleccion());
                id.setIdPlato(dto.getIdPlato());
                id.setDiaSemana(dto.getDiaSemana());

                detalle.setId(id);
                detalle.setSeleccion(seleccionGuardada);
                detalle.setCantidad(1);

                if (seleccionGuardada.getDetalles() == null) {
                    seleccionGuardada.setDetalles(new ArrayList<>());
                }
                seleccionGuardada.getDetalles().add(detalle);
            }
        }

        // El save final persiste la cabecera con sus detalles (vía CascadeType.ALL)
        return seleccionRepository.save(seleccionGuardada);
    }

    public List<Seleccion> obtenerPorLegajo(String legajo) {
        // CORRECCIÓN: El metodo debe coincidir con el nombre en el Repository
        return seleccionRepository.findByLegajoCliente(legajo);
    }
}