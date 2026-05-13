package com.utn.lunchflowbackend.controller;

import com.utn.lunchflowbackend.dto.SeleccionRequest;
import com.utn.lunchflowbackend.model.Seleccion;
import com.utn.lunchflowbackend.service.SeleccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/selecciones")
public class SeleccionController {

    @Autowired
    private SeleccionService seleccionService;

    // 1. Endpoint existente para guardar pedidos
    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmarSeleccion(@RequestBody SeleccionRequest request) {
        try {
            Seleccion guardada = seleccionService.guardarSeleccionCompleta(request);
            return ResponseEntity.ok(guardada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar la selección: " + e.getMessage());
        }
    }

    // 2. NUEVO: Endpoint para obtener el historial por legajo
    @GetMapping("/historial/{legajo}")
    public ResponseEntity<?> obtenerHistorial(@PathVariable String legajo) {
        try {
            // Llamamos al service para obtener las selecciones del usuario
            List<Seleccion> historial = seleccionService.obtenerPorLegajo(legajo);

            if (historial.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204 si no hay pedidos aún
            }

            return ResponseEntity.ok(historial);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al recuperar el historial: " + e.getMessage());
        }
    }
}