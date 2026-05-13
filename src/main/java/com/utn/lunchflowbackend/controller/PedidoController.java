package com.utn.lunchflowbackend.controller;

import com.utn.lunchflowbackend.dto.PedidoDTO;
import com.utn.lunchflowbackend.dto.ReportePedidoDTO;
import com.utn.lunchflowbackend.model.DetalleSeleccion;
import com.utn.lunchflowbackend.model.Pedido;
import com.utn.lunchflowbackend.model.Seleccion;
import com.utn.lunchflowbackend.repository.PedidoRepository;
import com.utn.lunchflowbackend.repository.PlatoRepository;
import com.utn.lunchflowbackend.service.SeleccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private SeleccionService seleccionService;

    @Autowired
    private PlatoRepository platoRepository;

    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @PostMapping
    public Pedido realizarPedido(@RequestBody Pedido pedido) {
        pedido.setFechaSeleccion(LocalDateTime.now());
        return pedidoRepository.save(pedido);
    }

    @GetMapping("/semana/{idMenu}")
    public List<Pedido> listarPorSemana(@PathVariable Long idMenu) {
        return pedidoRepository.findByMenuSemanalIdMenu(idMenu);
    }

    @GetMapping("/historial/{legajo}")
    public ResponseEntity<List<PedidoDTO>> obtenerHistorial(@PathVariable String legajo) {
        List<Seleccion> selecciones = seleccionService.obtenerPorLegajo(legajo);
        List<PedidoDTO> respuesta = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Seleccion s : selecciones) {
            String fechaTexto = (s.getFechaRegistro() != null)
                    ? s.getFechaRegistro().format(formatter)
                    : "S/F";

            StringBuilder detalleSemanal = new StringBuilder();

            if (s.getDetalles() != null && !s.getDetalles().isEmpty()) {
                // 1. Ordenamos la lista de detalles por el número de día
                List<DetalleSeleccion> detallesOrdenados = new ArrayList<>(s.getDetalles());
                detallesOrdenados.sort((d1, d2) -> d1.getId().getDiaSemana().compareTo(d2.getId().getDiaSemana()));

                for (DetalleSeleccion detalle : detallesOrdenados) {
                    Integer nroDia = detalle.getId().getDiaSemana();

                    String diaTexto;
                    switch (nroDia) {
                        case 1: diaTexto = "Lun"; break;
                        case 2: diaTexto = "Mar"; break;
                        case 3: diaTexto = "Mié"; break;
                        case 4: diaTexto = "Jue"; break;
                        case 5: diaTexto = "Vie"; break;
                        default: diaTexto = "Día " + nroDia; break;
                    }

                    Integer idInt = detalle.getId().getIdPlato();
                    String nombrePlato = platoRepository.findById(idInt.longValue())
                            .map(p -> p.getNombrePlato())
                            .orElse("Plato #" + idInt);

                    // Usamos \n para que en Android se vea uno debajo del otro
                    detalleSemanal.append(diaTexto).append(": ").append(nombrePlato).append("\n");
                }
            }

            String infoFinal = detalleSemanal.toString();

            // --- CORRECCIÓN CLAVE AQUÍ ---
            // Como ahora usamos "\n", hay que limpiar el último salto de línea, no el " | "
            if (infoFinal.endsWith("\n")) {
                infoFinal = infoFinal.substring(0, infoFinal.length() - 1);
            }

            if (infoFinal.isEmpty()) {
                infoFinal = "Sin platos seleccionados";
            }

            // Usamos el constructor que creamos en tu PedidoDTO (fecha, nombreMenu)
            respuesta.add(new PedidoDTO(fechaTexto, infoFinal));
        }

        return ResponseEntity.ok(respuesta);
    }
    @GetMapping("/exportar-reporte")
    public void exportarReporte(HttpServletResponse response) throws IOException {
        List<ReportePedidoDTO> reporte = pedidoRepository.obtenerResumenParaCatering();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reporte Catering");

        // 1. Estilo para moneda (Accounting)
        CellStyle moneyStyle = workbook.createCellStyle();
        DataFormat df = workbook.createDataFormat();
        moneyStyle.setDataFormat(df.getFormat("$#,##0.00"));

        // 2. Encabezados (Fila 0)
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Menú / Plato");
        header.createCell(1).setCellValue("Cantidad");
        header.createCell(2).setCellValue("Precio Unit.");
        header.createCell(3).setCellValue("Precio Total");

        // 3. Cargar datos y calcular
        int rowNum = 1;
        double granTotal = 0;

        for (ReportePedidoDTO item : reporte) {
            Row row = sheet.createRow(rowNum++);
            double totalPorPlato = item.getCantidad() * item.getPrecioUnitario();
            granTotal += totalPorPlato;

            row.createCell(0).setCellValue(item.getNombrePlato());
            row.createCell(1).setCellValue(item.getCantidad());

            Cell cellPrecioU = row.createCell(2);
            cellPrecioU.setCellValue(item.getPrecioUnitario());
            cellPrecioU.setCellStyle(moneyStyle);

            Cell cellTotalP = row.createCell(3);
            cellTotalP.setCellValue(totalPorPlato);
            cellTotalP.setCellStyle(moneyStyle);
        }

        // 4. Fila de Gran Total al final
        Row totalRow = sheet.createRow(rowNum + 1);
        totalRow.createCell(2).setCellValue("TOTAL FINAL:");
        Cell cellGranTotal = totalRow.createCell(3);
        cellGranTotal.setCellValue(granTotal);
        cellGranTotal.setCellStyle(moneyStyle);

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_catering_precios.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}