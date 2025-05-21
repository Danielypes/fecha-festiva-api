package com.example.fecha_festiva_api;

import java.time.LocalDate;

public class FechaUtil {

    public static LocalDate calcularDomingoDePascua(int anio) {
        int a = anio % 19;
        int b = anio % 4;
        int c = anio % 7;
        int d = (((19 * a) + 24) % 30);
        int e = (((2 * b) + (4 * c) + (6 * d) + 5) % 7);
        int dias = d + e;

        // Domingo de Pascua es el 15 de marzo + días calculados
        return LocalDate.of(anio, 3, 15).plusDays(dias + 7);
    }
}