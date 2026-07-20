-- kguanoluisa, DDL tabla jobs compartida entre servicios Python y Java, sin nuevas variables, 2026-07-17
-- kguanoluisa, Funcionamiento: crea la tabla jobs en Postgres; Python inserta filas y Java actualiza sentimiento y palabras clave, sin nuevas variables, 2026-07-17
CREATE TABLE IF NOT EXISTS jobs (
    id                    VARCHAR(36) PRIMARY KEY,
    texto                 TEXT NOT NULL,
    estado                VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    resultado_sentimiento   VARCHAR(50),
    palabras_clave        TEXT,
    fecha_creacion        TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    fecha_actualizacion   TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
