package com.utn.lunchflowbackend.dto;

public class PedidoDTO {
    // Estas variables DEBEN ser privadas pero sus nombres coincidir con Android
    private String fecha;
    private String nombreMenu;

    public PedidoDTO(String fecha, String nombreMenu) {
        this.fecha = fecha;
        this.nombreMenu = nombreMenu;
    }

    // SIN ESTOS GETTERS PÚBLICOS, EL JSON SALE VACÍO HACIA LA APP
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombreMenu() {
        return nombreMenu;
    }

    public void setNombreMenu(String nombreMenu) {
        this.nombreMenu = nombreMenu;
    }
}