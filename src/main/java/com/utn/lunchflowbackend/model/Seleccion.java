package com.utn.lunchflowbackend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(name = "legajo_user_seleccion")
    private String legajoCliente;

    @Column(name = "id_menu_seleccion")
    private Integer idMenu;

    @Column(name = "fecha_registro_seleccion")
    private LocalDateTime fechaRegistro;

    @OneToMany(mappedBy = "seleccion", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DetalleSeleccion> detalles;
}