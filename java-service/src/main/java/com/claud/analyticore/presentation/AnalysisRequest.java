package com.claud.analyticore.presentation;

// kguanoluisa, DTO de entrada para POST /analysis, clase AnalysisRequest, 2026-07-17
public class AnalysisRequest {

    private String jobId;
    private String texto;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
