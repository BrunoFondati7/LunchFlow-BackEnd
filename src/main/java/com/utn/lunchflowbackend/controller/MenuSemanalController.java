package com.utn.lunchflowbackend.controller;

import com.utn.lunchflowbackend.model.MenuSemanal;
import com.utn.lunchflowbackend.repository.MenuSemanalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu-semanal")
public class MenuSemanalController {

    @Autowired
    private MenuSemanalRepository menuSemanalRepository;

    // Obtener todos los menús creados (Historial)
    @GetMapping
    public List<MenuSemanal> listarMenus() {
        return menuSemanalRepository.findAll();
    }

    // Obtener el menú que está actualmente activo
    @GetMapping("/activo")
    public MenuSemanal obtenerMenuActivo() {
        return menuSemanalRepository.findByEstadoMenuTrue().orElse(null);
    }

    // Crear un nuevo período de menú (ej: Semana del 11 al 15 de Mayo)
    @PostMapping
    public MenuSemanal crearMenu(@RequestBody MenuSemanal menuSemanal) {
        return menuSemanalRepository.save(menuSemanal);
    }
}