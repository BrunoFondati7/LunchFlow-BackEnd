package com.utn.lunchflowbackend.repository;

import com.utn.lunchflowbackend.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    // Vacío también está perfecto.
}
