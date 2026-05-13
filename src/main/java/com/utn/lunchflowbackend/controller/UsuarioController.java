package com.utn.lunchflowbackend.controller;

import com.utn.lunchflowbackend.dto.LoginRequest;
import com.utn.lunchflowbackend.model.Usuario;
import com.utn.lunchflowbackend.repository.UsuarioRepository;
import com.utn.lunchflowbackend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*") // Para que Android y la Web se conecten sin dramas
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    // --- CRUD DE EMPLEADOS ---

    // Listar todos los usuarios (GET /api/usuarios)
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Guardar o Editar usuario (POST /api/usuarios/guardar)
    // Se usa tanto para crear como para actualizar en el modal
    @PostMapping("/guardar")
    public Usuario guardar(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Eliminar usuario (DELETE /api/usuarios/eliminar/{legajo})
    @DeleteMapping("/eliminar/{legajo}")
    public ResponseEntity<String> eliminar(@PathVariable("legajo") Long legajo) {
        usuarioRepository.deleteById(legajo);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }

    // --- MÓDULO DE ACCESO ---

    // POST para el Login (POST /api/usuarios/login)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Validamos a través del service que ya tenés armado
        Usuario usuario = usuarioService.validarLogin(loginRequest.getLegajo(), loginRequest.getPassword());

        if (usuario != null) {
            // ÉXITO: Devolvemos el usuario para que Android sepa quién entró
            return ResponseEntity.ok(usuario);
        } else {
            // ERROR: 401 Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Legajo o contraseña incorrectos");
        }
    }
}