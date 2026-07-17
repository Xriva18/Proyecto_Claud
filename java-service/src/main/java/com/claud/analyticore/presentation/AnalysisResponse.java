package com.claud.analyticore.presentation;

import java.util.List;

// kguanoluisa, DTO de salida para POST /analysis, clase AnalysisResponse, 2026-07-17
public class AnalysisResponse {

    private String sentimiento;
    private List<String> palabrasClave;

    public AnalysisResponse() {
    }

    public AnalysisResponse(String sentimiento, List<String> palabrasClave) {
        this.sentimiento = sentimiento;
        this.palabrasClave = palabrasClave;
    }

    public String getSentimiento() {
        return sentimiento;
    }

    public void setSentimiento(String sentimiento) {
        this.sentimiento = sentimiento;
    }

    public List<String> getPalabrasClave() {
        return palabrasClave;
    }

    public void setPalabrasClave(List<String> palabrasClave) {
        this.palabrasClave = palabrasClave;
    }
}
