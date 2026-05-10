package com.utn.lunchflowbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedido")
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    @ManyToOne
    @JoinColumn(name = "legajo_user", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_plato", nullable = false)
    private Plato plato;

    @ManyToOne
    @JoinColumn(name = "id_menu", nullable = false)
    private MenuSemanal menuSemanal;

    private LocalDateTime fechaSeleccion; // Cuándo hizo el clic en la App

    @Column(length = 255)
    private String observaciones; // Por si quiere "sin sal" o algo similar
}