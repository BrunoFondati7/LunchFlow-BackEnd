package com.utn.lunchflowbackend.controller;

import com.utn.lunchflowbackend.dto.SeleccionRequest;
import com.utn.lunchflowbackend.model.Seleccion;
import com.utn.lunchflowbackend.service.SeleccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/selecciones")
public class SeleccionController {

    @Autowired
    private SeleccionService seleccionService;

    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmarSeleccion(@RequestBody SeleccionRequest request) {
        try {
            Seleccion guardada = seleccionService.guardarSeleccionCompleta(request);
            return ResponseEntity.ok(guardada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar la selección: " + e.getMessage());
        }
    }
}