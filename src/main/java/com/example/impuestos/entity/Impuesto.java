package com.example.impuestos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "impuestos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Impuesto {

    @Id
    private Long sticker;

    @Column(name = "fecha_movimiento", nullable = false)
    private LocalDate fechaMovimiento;

    @Column(name = "fecha_recaudo", nullable = false)
    private LocalDate fechaRecaudo;

    @Column(name = "tipo_horario", length = 2)
    private String tipoHorario;

    @Column(name = "numero_id")
    private Long nroId;

    @Column(name = "numero_formulario")
    private Long nroForm;

    @Column(nullable = false)
    private Long valor;
}
