package com.utn.lunchflowbackend.repository;

import com.utn.lunchflowbackend.model.MenuSemanal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuSemanalRepository extends JpaRepository<MenuSemanal, Long> {

    Optional<MenuSemanal> findByEstadoMenuTrue();
}