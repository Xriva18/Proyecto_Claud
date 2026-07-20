# kguanoluisa, Dockerfile imagen Python con Uvicorn; puerto dinámico PORT para Render, sin nuevas variables, 2026-07-17
# kguanoluisa, Funcionamiento: instala requirements, copia el código y arranca main:app escuchando en ${PORT:-8000}, sin nuevas variables, 2026-07-17
FROM python:3.12-slim

WORKDIR /app

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY . .

ENV PYTHONPATH=/app

EXPOSE 8000

# kguanoluisa, Puerto dinámico para Render vía variable PORT con fallback local 8000, sin nuevas variables, 2026-07-17
CMD uvicorn main:app --host 0.0.0.0 --port ${PORT:-8000}
