package com.utn.lunchflowbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "menu_semanal")
@Data
public class MenuSemanal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMenu;

    private LocalDate fechaInicioMenu; // Ejemplo: 2026-05-11
    private LocalDate fechaFinMenu;    // Ejemplo: 2026-05-15
    private LocalDateTime fechaLimitePedido; // El viernes a las 16:00 hs

    private boolean estadoMenu = true; // Vigente o Finalizado
    private String obsMenu;
}