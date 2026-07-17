# Proyecto_Claud — Analyticore

Monorepo con tres servicios para análisis de texto: frontend React, servicio de submisión Python (FastAPI) y servicio de análisis Java (Spring Boot).

## Estructura

```
Proyecto_Claud/
├── frontend/          # React + Nginx
├── python-service/    # FastAPI — Submisión (POST/GET /jobs)
├── java-service/      # Spring Boot — Análisis (POST /analysis)
├── db/init.sql        # DDL tabla jobs compartida
└── docker-compose.yml # Prueba local
```

## Arquitectura

- **Frontend:** formulario de texto → polling a `GET /jobs/{id}` cada 2 s.
- **Python:** crea jobs en PostgreSQL, llama sincrónicamente al servicio Java en background.
- **Java:** analiza sentimiento y palabras clave, actualiza la misma tabla `jobs`.
- **PostgreSQL:** base `db_analyticore`, tabla compartida `jobs`.

## Prueba local con Docker

```bash
cp .env.example .env
docker compose up --build
```

Abrir http://localhost, enviar un texto y esperar el resultado.

## Variables de entorno

| Variable | Descripción |
|---|---|
| `DATABASE_URL` | Conexión PostgreSQL (local o Render) |
| `JAVA_SERVICE_URL` | URL del servicio Java (Python lo consume) |
| `VITE_API_URL` | URL base del API para el build del frontend |

**No commitear `.env` ni credenciales de Render.** Usar secrets en el panel de despliegue.

## Endpoints

| Servicio | Método | Ruta | Descripción |
|---|---|---|---|
| Python | POST | `/jobs` | Crear submission |
| Python | GET | `/jobs/{id}` | Consultar estado |
| Java | POST | `/analysis` | Procesar análisis (interno) |

## Despliegue en Render

Configurar `DATABASE_URL` con la URL **internal** de Render en cada servicio backend. La URL external solo sirve para debug manual desde tu máquina.
