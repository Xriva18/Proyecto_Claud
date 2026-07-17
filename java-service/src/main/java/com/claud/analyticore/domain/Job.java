package com.claud.analyticore.domain;

import java.time.Instant;
import java.util.List;

// kguanoluisa, Entidad de dominio Job con resultado de análisis, sin nuevas variables, 2026-07-17
public class Job {

    private String id;
    private String texto;
    private EstadoJob estado;
    private String resultadoSentimiento;
    private List<String> palabrasClave;
    private Instant fechaCreacion;
    private Instant fechaActualizacion;

    public Job() {
    }

    public Job(String id, String texto, EstadoJob estado) {
        this.id = id;
        this.texto = texto;
        this.estado = estado;
    }

    public void marcarCompletado(String sentimiento, List<String> palabrasClave) {
        this.estado = EstadoJob.COMPLETADO;
        this.resultadoSentimiento = sentimiento;
        this.palabrasClave = palabrasClave;
        this.fechaActualizacion = Instant.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public EstadoJob getEstado() {
        return estado;
    }

    public void setEstado(EstadoJob estado) {
        this.estado = estado;
    }

    public String getResultadoSentimiento() {
        return resultadoSentimiento;
    }

    public void setResultadoSentimiento(String resultadoSentimiento) {
        this.resultadoSentimiento = resultadoSentimiento;
    }

    public List<String> getPalabrasClave() {
        return palabrasClave;
    }

    public void setPalabrasClave(List<String> palabrasClave) {
        this.palabrasClave = palabrasClave;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Instant getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Instant fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
