package com.claud.analyticore.domain;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// kguanoluisa, Pruebas unitarias de la entidad Job y su método marcarCompletado, sin nuevas variables, 2026-07-30
class JobTest {

    @Test
    void marcarCompletadoActualizaEstadoYResultado() {
        Job job = new Job("job-1", "texto de prueba", EstadoJob.PENDIENTE);

        job.marcarCompletado("POSITIVO", List.of("prueba", "texto"));

        assertEquals(EstadoJob.COMPLETADO, job.getEstado());
        assertEquals("POSITIVO", job.getResultadoSentimiento());
        assertEquals(List.of("prueba", "texto"), job.getPalabrasClave());
        assertNotNull(job.getFechaActualizacion());
    }
}
