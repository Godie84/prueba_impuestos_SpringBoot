package com.example.impuestos.controller;

import com.example.impuestos.dto.CantidadValorDTO;
import com.example.impuestos.entity.Impuesto;
import com.example.impuestos.service.CargarCSVService;
import com.example.impuestos.service.ImpuestoService;
import com.example.impuestos.util.ExportarExcel;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/impuestos")
@RequiredArgsConstructor
public class ImpuestoController {

    private final ImpuestoService service;
    private final CargarCSVService cargador;

    @PostMapping
    public ResponseEntity<String> crear(@RequestBody Impuesto imp) {
        boolean ok = service.insertarImpuesto(imp);
        return ok ? ResponseEntity.ok("Insertado correctamente") : ResponseEntity.status(409).body("Sticker duplicado");
    }

    @GetMapping("/fecha/{fecha}")
    public List<Impuesto> porFecha(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return service.obtenerPorFecha(fecha);
    }

    @GetMapping("/{sticker}")
    public ResponseEntity<Impuesto> porSticker(@PathVariable Long sticker) {
        return service.obtenerPorSticker(sticker)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/horario/{tipo}")
    public CantidadValorDTO cantidadYValor(@PathVariable String tipo) {
        return service.obtenerCantidadValorPorHorario(tipo);
    }

    // Endpoint para subir CSV (multipart) y procesarlo
    @PostMapping(path = "/upload-csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) throws IOException {
        int[] resultados = cargador.cargarArchivoDesdeBytes(file.getBytes());
        String msg = String.format("Total procesadas=%d, insertadas=%d, errFecha=%d, errNum=%d, errGen=%d",
                resultados[0], resultados[1], resultados[2], resultados[3], resultados[4]);
        return ResponseEntity.ok(msg);
    }

    // Endpoint para exportar un impuesto a Excel (descarga)
    @GetMapping(path = "/{sticker}/export", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ResponseEntity<byte[]> exportarExcel(@PathVariable Long sticker) throws IOException {
        return service.obtenerPorSticker(sticker)
                .map(imp -> {
                    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                        ExportarExcel.exportarImpuestoAExcel(imp, baos);
                        byte[] bytes = baos.toByteArray();
                        return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=impuesto_" + sticker + ".xlsx")
                                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                                .body(bytes);
                    } catch (IOException e) {
                        return ResponseEntity.status(500).build();
                    }
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
