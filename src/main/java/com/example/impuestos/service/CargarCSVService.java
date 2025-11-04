package com.example.impuestos.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.example.impuestos.entity.Impuesto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CargarCSVService {

    private final ImpuestoService impuestoService;

    /**
     * Carga un CSV desde un arreglo de bytes (por ejemplo MultipartFile#getBytes())
     * Devuelve un arreglo de enteros con: [total, insertados, errFecha, errNum, errGen]
     */
    public int[] cargarArchivoDesdeBytes(byte[] bytes) {
        int total = 0, insertados = 0, errFecha = 0, errNum = 0, errGen = 0;

        try (Reader reader = new InputStreamReader(new ByteArrayInputStream(bytes), StandardCharsets.UTF_8);
             CSVReader csv = new CSVReader(reader)) {

            List<String[]> filas = csv.readAll();
            boolean primero = true;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

            for (String[] fila : filas) {
                if (primero) { primero = false; continue; }
                total++;
                try {
                    long sticker = Long.parseLong(fila[0].trim());
                    LocalDate fechaMov = LocalDate.parse(fila[1].trim(), formatter);
                    LocalDate fechaRec = LocalDate.parse(fila[2].trim(), formatter);
                    String tipo = fila[3].trim();
                    long nroId = Long.parseLong(fila[4].trim());
                    long nroForm = Long.parseLong(fila[5].trim());
                    long valor = Long.parseLong(fila[6].trim());

                    Impuesto imp = Impuesto.builder()
                            .sticker(sticker)
                            .fechaMovimiento(fechaMov)
                            .fechaRecaudo(fechaRec)
                            .tipoHorario(tipo)
                            .nroId(nroId)
                            .nroForm(nroForm)
                            .valor(valor)
                            .build();

                    boolean ok = impuestoService.insertarImpuesto(imp);
                    if (ok) insertados++;

                } catch (java.time.format.DateTimeParseException ex) {
                    errFecha++;
                } catch (NumberFormatException ex) {
                    errNum++;
                } catch (Exception ex) {
                    errGen++;
                }
            }
        } catch (Exception e) {
            // Problema leyendo el CSV
            errGen++;
        }

        return new int[]{total, insertados, errFecha, errNum, errGen};
    }
}
