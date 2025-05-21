package com.example.fecha_festiva_api.repositorio;

import com.example.fecha_festiva_api.model.Festivo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface FestivoRepositorio extends JpaRepository<Festivo, Long> {
    List<Festivo> findByDiaAndMesAndTipo(int dia, int mes, int tipo);
    List<Festivo> findByTipo(int tipo);
}

