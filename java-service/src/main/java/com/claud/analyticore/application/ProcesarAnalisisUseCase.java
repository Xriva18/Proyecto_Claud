package com.claud.analyticore.application;

import com.claud.analyticore.domain.Job;
import com.claud.analyticore.infrastructure.analysis.AnalysisResult;
import com.claud.analyticore.infrastructure.analysis.SimpleTextAnalyzer;
import com.claud.analyticore.infrastructure.persistence.JobRepositoryAdapter;

import org.springframework.stereotype.Service;

// kguanoluisa, Caso de uso procesar análisis de sentimiento y palabras clave, clase ProcesarAnalisisUseCase, 2026-07-17
@Service
public class ProcesarAnalisisUseCase {

    private final JobRepositoryAdapter jobRepository;
    private final SimpleTextAnalyzer textAnalyzer;

    public ProcesarAnalisisUseCase(JobRepositoryAdapter jobRepository, SimpleTextAnalyzer textAnalyzer) {
        this.jobRepository = jobRepository;
        this.textAnalyzer = textAnalyzer;
    }

    public AnalysisResult ejecutar(String jobId, String texto) {
        Job job = jobRepository.buscarPorId(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job no encontrado: " + jobId));

        AnalysisResult result = textAnalyzer.analizar(texto);
        job.marcarCompletado(result.sentimiento(), result.palabrasClave());
        jobRepository.actualizarResultado(job);

        return result;
    }
}
