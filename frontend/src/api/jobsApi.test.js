// kguanoluisa, Pruebas unitarias del cliente jobsApi con fetch mockeado, sin nuevas variables, 2026-07-30
import { describe, it, expect, vi, beforeEach } from 'vitest';
import { crearJob, consultarJob } from './jobsApi';

describe('jobsApi', () => {
  beforeEach(() => {
    global.fetch = vi.fn();
  });

  it('crearJob envia POST y devuelve el job creado', async () => {
    const mockJob = { id: '123', estado: 'PENDIENTE' };
    global.fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockJob,
    });

    const resultado = await crearJob('texto de prueba');

    expect(global.fetch).toHaveBeenCalledWith(
      expect.stringContaining('/jobs/'),
      expect.objectContaining({ method: 'POST' })
    );
    expect(resultado).toEqual(mockJob);
  });

  it('crearJob lanza error si la respuesta no es ok', async () => {
    global.fetch.mockResolvedValueOnce({
      ok: false,
      json: async () => ({ detail: 'Texto invalido' }),
    });

    await expect(crearJob('')).rejects.toThrow('Texto invalido');
  });

  it('consultarJob devuelve el estado del job', async () => {
    const mockJob = { id: '123', estado: 'COMPLETADO' };
    global.fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockJob,
    });

    const resultado = await consultarJob('123');

    expect(global.fetch).toHaveBeenCalledWith(expect.stringContaining('/jobs/123'));
    expect(resultado).toEqual(mockJob);
  });
});
