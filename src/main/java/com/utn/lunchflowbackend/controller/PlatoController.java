package com.utn.lunchflowbackend.controller;

import com.utn.lunchflowbackend.model.Plato;
import com.utn.lunchflowbackend.repository.PlatoRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/platos")
public class PlatoController {

    @Autowired
    private PlatoRepository platoRepository;

    // Obtener todos los platos para el menú semanal
    @GetMapping
    public List<Plato> listarPlatos() {
        return platoRepository.findAll();
    }

    // Crear un nuevo plato (Catálogo del Admin)
    @PostMapping
    public Plato guardarPlato(@RequestBody Plato plato) {
        return platoRepository.save(plato);
    }

    // Borrar un plato
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPlato(@PathVariable Long id) {
        platoRepository.deleteById(id);
        return ResponseEntity.ok("Plato eliminado");
    }

    // Actualizar un plato (Modificación)
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Plato> actualizarPlato(@PathVariable Long id, @RequestBody Plato platoDetalles) {
        return platoRepository.findById(id).map(plato -> {
            plato.setNombrePlato(platoDetalles.getNombrePlato());
            plato.setDescripcionPlato(platoDetalles.getDescripcionPlato());
            plato.setPrecioPlato(platoDetalles.getPrecioPlato());
            return ResponseEntity.ok(platoRepository.save(plato));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/importar")
    public ResponseEntity<String> importarExcel(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Archivo vacío. Por favor, seleccioná un Excel.");
        }

        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            List<Plato> nuevosPlatos = new ArrayList<>();

            for (Row row : sheet) {
                // 1. Saltamos la fila de encabezados
                if (row.getRowNum() == 0) continue;

                // 2. Verificamos que la celda del nombre (Columna A) no esté vacía
                Cell cellNombre = row.getCell(0);
                if (cellNombre == null || cellNombre.getCellType() == CellType.BLANK) continue;

                Plato plato = new Plato();

                // --- COLUMNA A (0): nombrePlato ---
                plato.setNombrePlato(cellNombre.getStringCellValue());

                // --- COLUMNA B (1): descripcionPlato ---
                Cell cellDesc = row.getCell(1);
                if (cellDesc != null && cellDesc.getCellType() != CellType.BLANK) {
                    plato.setDescripcionPlato(cellDesc.getStringCellValue()); // <--- CORREGIDO
                } else {
                    plato.setDescripcionPlato("Sin descripción");
                }

                // --- COLUMNA C (2): precioPlato ---
                Cell cellPrecio = row.getCell(2);
                if (cellPrecio != null) {
                    if (cellPrecio.getCellType() == CellType.NUMERIC) {
                        plato.setPrecioPlato(cellPrecio.getNumericCellValue()); // <--- CORREGIDO
                    } else if (cellPrecio.getCellType() == CellType.STRING) {
                        try {
                            String val = cellPrecio.getStringCellValue().replaceAll("[^\\d.]", "");
                            plato.setPrecioPlato(Double.parseDouble(val)); // <--- CORREGIDO
                        } catch (NumberFormatException e) {
                            plato.setPrecioPlato(0.0);
                        }
                    }
                } else {
                    plato.setPrecioPlato(0.0);
                }

                // Opcional: Podés marcarlo como activo por defecto
                plato.setActivoPlato(true);

                nuevosPlatos.add(plato);
            }

            if (!nuevosPlatos.isEmpty()) {
                platoRepository.saveAll(nuevosPlatos);
                return ResponseEntity.ok("Éxito: Se importaron " + nuevosPlatos.size() + " platos.");
            } else {
                return ResponseEntity.ok("No se encontraron platos válidos.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar Excel: " + e.getMessage());
        }
    }
}