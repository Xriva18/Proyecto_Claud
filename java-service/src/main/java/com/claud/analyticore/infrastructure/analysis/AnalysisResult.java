package com.claud.analyticore.infrastructure.analysis;

import java.util.List;

// kguanoluisa, Record con resultado del análisis de texto, sin nuevas variables, 2026-07-17
// kguanoluisa, Funcionamiento: transporta sentimiento y palabras clave desde SimpleTextAnalyzer hacia el caso de uso, sin nuevas variables, 2026-07-17
public record AnalysisResult(String sentimiento, List<String> palabrasClave) {
}
