package com.utn.lunchflowbackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuario") // Nombre de la tabla en Postgres
@Data
public class Usuario {

    @Id
    @Column(name = "legajo_user", length = 50)
    private String legajoUser; // PK [cite: 9]
    private String nombreUser; // [cite: 21]
    private String apellidoUser; // [cite: 22]
    private String dniUser; // [cite: 24]
    private String mailUser; // [cite: 23]
    private String passUser; // [cite: 26]
    private boolean adminUser; // true para Admin, false para Empleado [cite: 12]
    private boolean activoUser = true; // Para el borrado lógico [cite: 25, 28]
}
