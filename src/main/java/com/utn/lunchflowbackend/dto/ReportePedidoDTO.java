package com.utn.lunchflowbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportePedidoDTO {
    private String nombrePlato;
    private Long cantidad;
    private Double precioUnitario;
}