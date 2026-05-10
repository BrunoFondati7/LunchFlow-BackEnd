package com.utn.lunchflowbackend.controller;

import com.utn.lunchflowbackend.model.Plato;
import com.utn.lunchflowbackend.repository.PlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/platos")
public class PlatoController {

    @Autowired
    private PlatoRepository platoRepository;

    // Obtener todos los platos para el menú semanal
    @GetMapping
    public List<Plato> listarPlatos() {
        return platoRepository.findAll();
    }

    // Crear un nuevo plato (Catálogo del Admin)
    @PostMapping
    public Plato guardarPlato(@RequestBody Plato plato) {
        return platoRepository.save(plato);
    }
}