package com.claud.analyticore.infrastructure.analysis;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

// kguanoluisa, Analizador simple de sentimiento y palabras clave sin ML, clase SimpleTextAnalyzer, 2026-07-17
// kguanoluisa, Funcionamiento: tokeniza el texto, compara diccionarios positivo/negativo y devuelve sentimiento más frecuentes, sin nuevas variables, 2026-07-17
@Component
public class SimpleTextAnalyzer {

    private static final Set<String> POSITIVAS = Set.of(
            "bueno", "buena", "excelente", "genial", "feliz", "amor", "gracias",
            "maravilloso", "perfecto", "increible", "fantastico", "bien"
    );

    private static final Set<String> NEGATIVAS = Set.of(
            "malo", "mala", "terrible", "triste", "odio", "horrible", "pesimo",
            "decepcion", "enojo", "molesto", "fatal", "mal"
    );

    private static final Set<String> STOPWORDS = Set.of(
            "para", "como", "este", "esta", "estos", "estas", "pero", "porque",
            "donde", "cuando", "desde", "hasta", "sobre", "entre", "con", "sin",
            "que", "del", "los", "las", "una", "uno", "the", "and", "for"
    );

    public AnalysisResult analizar(String texto) {
        String normalizado = texto.toLowerCase(Locale.ROOT);
        List<String> tokens = Arrays.stream(normalizado.split("[^a-zA-ZáéíóúñÁÉÍÓÚÑ]+"))
                .filter(token -> token.length() > 3)
                .filter(token -> !STOPWORDS.contains(token))
                .toList();

        int positivos = contarCoincidencias(tokens, POSITIVAS);
        int negativos = contarCoincidencias(tokens, NEGATIVAS);
        String sentimiento = calcularSentimiento(positivos, negativos);
        List<String> palabrasClave = extraerPalabrasClave(tokens);

        return new AnalysisResult(sentimiento, palabrasClave);
    }

    private int contarCoincidencias(List<String> tokens, Set<String> diccionario) {
        int count = 0;
        for (String token : tokens) {
            if (diccionario.contains(token)) {
                count++;
            }
        }
        return count;
    }

    private String calcularSentimiento(int positivos, int negativos) {
        if (positivos > negativos) {
            return "POSITIVO";
        }
        if (negativos > positivos) {
            return "NEGATIVO";
        }
        return "NEUTRO";
    }

    private List<String> extraerPalabrasClave(List<String> tokens) {
        Map<String, Integer> frecuencia = new HashMap<>();
        for (String token : tokens) {
            frecuencia.merge(token, 1, Integer::sum);
        }

        return frecuencia.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
