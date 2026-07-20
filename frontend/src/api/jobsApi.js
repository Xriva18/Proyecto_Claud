// kguanoluisa, Cliente API para crear jobs y consultar estado, constante API_BASE, 2026-07-17
// kguanoluisa, Funcionamiento: usa VITE_API_URL (Python en Render o localhost:8000) para POST /jobs y GET /jobs/{id}, sin nuevas variables, 2026-07-17
const API_BASE = import.meta.env.VITE_API_URL || 'http://localhost:8000';

export async function crearJob(texto) {
  const response = await fetch(`${API_BASE}/jobs/`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ texto }),
  });

  if (!response.ok) {
    const error = await response.json().catch(() => ({}));
    throw new Error(error.detail || 'Error al crear el job');
  }

  return response.json();
}

export async function consultarJob(jobId) {
  const response = await fetch(`${API_BASE}/jobs/${jobId}`);

  if (!response.ok) {
    const error = await response.json().catch(() => ({}));
    throw new Error(error.detail || 'Error al consultar el job');
  }

  return response.json();
}
