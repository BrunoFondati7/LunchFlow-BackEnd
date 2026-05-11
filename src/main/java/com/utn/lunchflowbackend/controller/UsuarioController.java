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
@CrossOrigin(origins = "*") // Importante para la conexión con Android
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    // GET para ver todos los usuarios (http://localhost:8080/api/usuarios)
    @GetMapping
    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    // POST para crear un usuario nuevo (útil para registrarse)
    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // POST para el Login (http://localhost:8080/api/usuarios/login)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Llamamos al servicio con los datos del DTO que ya creaste
        Usuario usuario = usuarioService.validarLogin(loginRequest.getLegajo(), loginRequest.getPassword());

        if (usuario != null) {
            // ÉXITO: Devolvemos el usuario completo (incluyendo nombre, legajo, etc.)
            return ResponseEntity.ok(usuario);
        } else {
            // ERROR: Credenciales inválidas o usuario inexistente
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Legajo o contraseña incorrectos");
        }
    }
}