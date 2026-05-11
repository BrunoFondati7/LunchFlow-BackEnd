package com.utn.lunchflowbackend.dto;

import lombok.Data;
import java.util.List;

@Data
public class SeleccionRequest {
    // Corresponde a 'legajo_user_seleccion' en el DER
    private String legajoUser;

    // Corresponde a 'id_menu_seleccion' en el DER
    private Integer idMenu;

    // Lista de los 5 platos (uno por día)
    private List<DetalleSeleccionDTO> detalles;

    @Data
    public static class DetalleSeleccionDTO {
        // Corresponde a 'id_plato_detalle'
        private Integer idPlato;

        // Corresponde a 'dia_semana_detalle' (1=Lun, 2=Mar, etc.)
        private Integer diaSemana;

        // Corresponde a 'cantidad_detalle' (usualmente 1)
        private Integer cantidad;
    }
}