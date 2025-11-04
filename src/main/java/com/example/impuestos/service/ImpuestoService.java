package com.example.impuestos.service;

import com.example.impuestos.dto.CantidadValorDTO;
import com.example.impuestos.entity.Impuesto;
import com.example.impuestos.repository.ImpuestoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImpuestoService {

    private final ImpuestoRepository repo;

    @Transactional
    public boolean insertarImpuesto(Impuesto imp) {
        if (repo.existsById(imp.getSticker())) return false;
        repo.save(imp);
        return true;
    }

    @Transactional(readOnly = true)
    public List<Impuesto> obtenerPorFecha(LocalDate fecha) {
        return repo.findByFechaMovimiento(fecha);
    }

    @Transactional(readOnly = true)
    public Optional<Impuesto> obtenerPorSticker(Long sticker) {
        return repo.findById(sticker);
    }

    @Transactional(readOnly = true)
    public CantidadValorDTO obtenerCantidadValorPorHorario(String tipoHorario) {
        Object[] result = repo.obtenerCantidadYValorPorHorario(tipoHorario);
        if (result != null && result.length == 2 && result[0] != null) {
            Number count = (Number) result[0];
            Number sum = (Number) result[1];
            int cantidad = count != null ? count.intValue() : 0;
            long suma = sum != null ? sum.longValue() : 0L;
            return new CantidadValorDTO(cantidad, suma);
        }
        return new CantidadValorDTO(0, 0);
    }
}
