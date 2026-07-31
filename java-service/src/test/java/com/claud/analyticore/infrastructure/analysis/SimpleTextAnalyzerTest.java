package com.claud.analyticore.infrastructure.analysis;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// kguanoluisa, Pruebas unitarias de SimpleTextAnalyzer para sentimiento y palabras clave, sin nuevas variables, 2026-07-30
class SimpleTextAnalyzerTest {

    private final SimpleTextAnalyzer analyzer = new SimpleTextAnalyzer();

    @Test
    void devuelvePositivoCuandoHayMasPalabrasPositivas() {
        AnalysisResult resultado = analyzer.analizar("Este proyecto es excelente y me hace muy feliz, gracias");
        assertEquals("POSITIVO", resultado.sentimiento());
    }

    @Test
    void devuelveNegativoCuandoHayMasPalabrasNegativas() {
        AnalysisResult resultado = analyzer.analizar("Todo salio terrible y horrible, que decepcion tan grande");
        assertEquals("NEGATIVO", resultado.sentimiento());
    }

    @Test
    void devuelveNeutroCuandoNoHaySentimientoClaro() {
        AnalysisResult resultado = analyzer.analizar("El sistema procesa datos todos los dias del mes");
        assertEquals("NEUTRO", resultado.sentimiento());
    }

    @Test
    void extraeHastaCincoPalabrasClave() {
        AnalysisResult resultado = analyzer.analizar("Analisis analisis analisis proyecto proyecto sistema datos calidad rendimiento");
        assertTrue(resultado.palabrasClave().size() <= 5);
        assertTrue(resultado.palabrasClave().contains("analisis"));
    }
}
