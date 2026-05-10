package com.utn.lunchflowbackend.repository;

import com.utn.lunchflowbackend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    // JpaRepository ya gestiona la conexión con Postgres
}