package com.utn.lunchflowbackend.repository;

import com.utn.lunchflowbackend.model.DetalleSeleccion;
import com.utn.lunchflowbackend.model.DetalleSeleccionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleSeleccionRepository extends JpaRepository<DetalleSeleccion, DetalleSeleccionId> {
}