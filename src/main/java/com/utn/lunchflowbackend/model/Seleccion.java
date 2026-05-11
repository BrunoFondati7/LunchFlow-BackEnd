package com.utn.lunchflowbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "seleccion")
@Data
public class Seleccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seleccion")
    private Integer idSeleccion;

    @Column(name = "legajo_user_seleccion", length = 45)
    private String legajoUser;

    @Column(name = "id_menu_seleccion")
    private Integer idMenu;

    @Column(name = "fecha_registro_seleccion")
    private LocalDateTime fechaRegistro;

    // Relación con los detalles
    @OneToMany(mappedBy = "seleccion", cascade = CascadeType.ALL)
    private List<DetalleSeleccion> detalles;
}