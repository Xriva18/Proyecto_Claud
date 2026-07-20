package com.claud.analyticore.domain;

// kguanoluisa, Enum de estados del job en la capa de dominio Java, sin nuevas variables, 2026-07-17
// kguanoluisa, Funcionamiento: define el ciclo PENDIENTE → PROCESANDO → COMPLETADO compartido con Python y la BD, sin nuevas variables, 2026-07-17
public enum EstadoJob {
    PENDIENTE,
    PROCESANDO,
    COMPLETADO
}
