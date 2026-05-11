package com.utn.lunchflowbackend.repository;

import com.utn.lunchflowbackend.model.Seleccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeleccionRepository extends JpaRepository<Seleccion, Integer> {
}