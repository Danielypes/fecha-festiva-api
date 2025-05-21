package com.example.fecha_festiva_api.servicio;

import com.example.fecha_festiva_api.FechaUtil;
import com.example.fecha_festiva_api.model.Festivo;
import com.example.fecha_festiva_api.repositorio.FestivoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class FestivoServicio {

    @Autowired
    private FestivoRepositorio festivoRepositorio;

    public boolean esFestivo(LocalDate fecha) {
        int dia = fecha.getDayOfMonth();
        int mes = fecha.getMonthValue();
        int anio = fecha.getYear();

        // Verifica los festivos fijos
        Optional<Festivo> festivoFijo = festivoRepositorio.findAll().stream()
                .filter(f -> f.getDia() == dia && f.getMes() == mes && f.getTipo() == 1)
                .findFirst();

        if (festivoFijo.isPresent()) {
            return true;
        }

        // Verifica los festivos móviles, como el Día de la Raza, que puede trasladarse al lunes
        LocalDate pascua = FechaUtil.calcularDomingoDePascua(anio);

        for (Festivo festivo : festivoRepositorio.findAll()) {
            LocalDate fechaFestivo = obtenerFechaFestivo(festivo, pascua, anio);
            if (fechaFestivo != null && fechaFestivo.isEqual(fecha)) {
                return true;
            }
        }

        return false;
    }

    private LocalDate obtenerFechaFestivo(Festivo festivo, LocalDate pascua, int anio) {
        LocalDate fechaFestivo;

        switch (festivo.getTipo()) {
            case 1: // Festivo fijo
                fechaFestivo = LocalDate.of(anio, festivo.getMes(), festivo.getDia());
                break;

            case 2: // Ley de "Puente festivo" (traslado al lunes)
                fechaFestivo = LocalDate.of(anio, festivo.getMes(), festivo.getDia());
                if (fechaFestivo.getDayOfWeek() != DayOfWeek.MONDAY) {
                    fechaFestivo = fechaFestivo.with(DayOfWeek.MONDAY).plusWeeks(1);
                }
                break;

            case 3: // Basado en Pascua
                fechaFestivo = pascua.plusDays(festivo.getDiasPascua());
                break;

            case 4: // Basado en Pascua y trasladado al lunes
                fechaFestivo = pascua.plusDays(festivo.getDiasPascua());
                if (fechaFestivo.getDayOfWeek() != DayOfWeek.MONDAY) {
                    fechaFestivo = fechaFestivo.with(DayOfWeek.MONDAY).plusWeeks(1);
                }
                break;

            default:
                return null;
        }
        return fechaFestivo;
    }

}
