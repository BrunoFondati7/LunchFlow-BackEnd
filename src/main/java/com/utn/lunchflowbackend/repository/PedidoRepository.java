package com.utn.lunchflowbackend.repository;

import com.utn.lunchflowbackend.dto.ReportePedidoDTO;
import com.utn.lunchflowbackend.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Buscar todos los pedidos de una semana específica (para el balance)
    List<Pedido> findByMenuSemanalIdMenu(Long idMenu);

    // Buscar si un usuario ya pidió esta semana (para evitar duplicados)
    boolean existsByUsuarioLegajoUserAndMenuSemanalIdMenu(String legajo, Long idMenu);

    @Query("SELECT new com.utn.lunchflowbackend.dto.ReportePedidoDTO(p.nombrePlato, COUNT(d), p.precioPlato) " +
            "FROM Seleccion s " +
            "JOIN s.detalles d " +
            "JOIN d.plato p " +
            "GROUP BY p.nombrePlato, p.precioPlato")
    List<ReportePedidoDTO> obtenerResumenParaCatering();
}