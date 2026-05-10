package com.utn.lunchflowbackend.repository;

import com.utn.lunchflowbackend.model.Plato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, Long> {
    // No hace falta escribir nada acá por ahora.
}