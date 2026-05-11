package com.utn.lunchflowbackend.service;

import com.utn.lunchflowbackend.model.Usuario;
import com.utn.lunchflowbackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario validarLogin(String legajo, String password) {
        // Usamos el nombre que definiste en el Repository: findByLegajoUser
        return usuarioRepository.findByLegajoUser(legajo)
                .filter(user -> user.getPassUser().equals(password)) // Usamos getPassUser()
                .orElse(null);
    }
}