package com.utn.lunchflowbackend.controller;

import com.utn.lunchflowbackend.model.Categoria;
import com.utn.lunchflowbackend.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Listar categorías para que el Admin elija al crear un plato
    @GetMapping
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    // Crear nuevas categorías (ej: Minutas, Saludable, Vegano)
    @PostMapping
    public Categoria guardarCategoria(@RequestBody Categoria categoria) {
        return categoriaRepository.save(categoria);
    }
}