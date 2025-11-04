package com.example.impuestos.util;

import com.example.impuestos.entity.Impuesto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ExportarExcel {

    public static void exportarImpuestoAExcel(Impuesto imp, ByteArrayOutputStream outputStream) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sticker_" + imp.getSticker());

            String[] columnas = {
                    "Sticker",
                    "Fecha de Movimiento",
                    "Tipo de Horario",
                    "Fecha de Recaudo",
                    "Número de Identificación",
                    "Número de Formulario",
                    "Valor"
            };

            Row header = sheet.createRow(0);
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(columnas[i]);
            }

            Row fila = sheet.createRow(1);
            fila.createCell(0).setCellValue(imp.getSticker());
            fila.createCell(1).setCellValue(imp.getFechaMovimiento().toString());
            fila.createCell(2).setCellValue(imp.getTipoHorario());
            fila.createCell(3).setCellValue(imp.getFechaRecaudo().toString());
            fila.createCell(4).setCellValue(imp.getNroId());
            fila.createCell(5).setCellValue(imp.getNroForm());
            fila.createCell(6).setCellValue(imp.getValor());

            for (int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(outputStream);
        }
    }
}
