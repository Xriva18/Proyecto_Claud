package com.claud.analyticore.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

// kguanoluisa, Repositorio Spring Data JPA para la entidad JobEntity, sin nuevas variables, 2026-07-17
public interface JobJpaRepository extends JpaRepository<JobEntity, String> {
}
