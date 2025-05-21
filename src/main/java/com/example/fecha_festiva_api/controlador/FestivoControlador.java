package com.example.fecha_festiva_api.controlador;

import com.example.fecha_festiva_api.servicio.FestivoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/festivos")
public class FestivoControlador {

    @Autowired
    private FestivoServicio festivoServicio;

    @GetMapping("/es-festivo")
    public ResponseEntity<String> esFestivo(@RequestParam("fecha") String fechaStr) {
        try {
            LocalDate fecha = LocalDate.parse(fechaStr);
            boolean esFestivo = festivoServicio.esFestivo(fecha);
            return ResponseEntity.ok(esFestivo ? "La fecha es festiva" : "La fecha no es festiva");
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Fecha no válida");
        }
    }
}
