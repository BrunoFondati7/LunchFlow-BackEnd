package com.utn.lunchflowbackend.controller;

import com.utn.lunchflowbackend.model.Pedido;
import com.utn.lunchflowbackend.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @PostMapping
    public Pedido realizarPedido(@RequestBody Pedido pedido) {
        // Seteamos la fecha y hora actual del servidor al recibir el pedido
        pedido.setFechaSeleccion(LocalDateTime.now());
        return pedidoRepository.save(pedido);
    }

    @GetMapping("/semana/{idMenu}")
    public List<Pedido> listarPorSemana(@PathVariable Long idMenu) {
        return pedidoRepository.findByMenuSemanalIdMenu(idMenu);
    }
}
