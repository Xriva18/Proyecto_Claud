package com.claud.analyticore.infrastructure.persistence;

import com.claud.analyticore.domain.EstadoJob;
import com.claud.analyticore.domain.Job;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

// kguanoluisa, Adaptador de persistencia JPA con mapeo entidad-dominio, clase JobRepositoryAdapter, 2026-07-17
// kguanoluisa, Funcionamiento: busca jobs por id y guarda sentimiento/palabras clave serializadas en la tabla jobs, sin nuevas variables, 2026-07-17
@Repository
public class JobRepositoryAdapter {

    private final JobJpaRepository jpaRepository;
    private final ObjectMapper objectMapper;

    public JobRepositoryAdapter(JobJpaRepository jpaRepository, ObjectMapper objectMapper) {
        this.jpaRepository = jpaRepository;
        this.objectMapper = objectMapper;
    }

    public Optional<Job> buscarPorId(String id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    public void actualizarResultado(Job job) {
        JobEntity entity = jpaRepository.findById(job.getId())
                .orElseThrow(() -> new IllegalArgumentException("Job no encontrado: " + job.getId()));

        entity.setEstado(job.getEstado().name());
        entity.setResultadoSentimiento(job.getResultadoSentimiento());
        entity.setPalabrasClave(serializarPalabrasClave(job.getPalabrasClave()));
        entity.setFechaActualizacion(Instant.now());

        jpaRepository.save(entity);
    }

    private Job toDomain(JobEntity entity) {
        Job job = new Job(entity.getId(), entity.getTexto(), EstadoJob.valueOf(entity.getEstado()));
        job.setResultadoSentimiento(entity.getResultadoSentimiento());
        job.setPalabrasClave(deserializarPalabrasClave(entity.getPalabrasClave()));
        if (entity.getFechaCreacion() != null) {
            job.setFechaCreacion(entity.getFechaCreacion());
        }
        if (entity.getFechaActualizacion() != null) {
            job.setFechaActualizacion(entity.getFechaActualizacion());
        }
        return job;
    }

    private String serializarPalabrasClave(List<String> palabrasClave) {
        try {
            return objectMapper.writeValueAsString(palabrasClave);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Error serializando palabras clave", e);
        }
    }

    private List<String> deserializarPalabrasClave(String json) {
        if (json == null || json.isBlank()) {
            return List.of();
        }
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Error deserializando palabras clave", e);
        }
    }
}
