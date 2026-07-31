# kguanoluisa, Pruebas unitarias de la entidad Job y transiciones de estado, sin nuevas variables, 2026-07-30
from domain.job import Job, EstadoJob


def test_job_inicia_pendiente():
    job = Job(texto="prueba")
    assert job.estado == EstadoJob.PENDIENTE


def test_marcar_procesando_cambia_estado():
    job = Job(texto="prueba")
    job.marcar_procesando()
    assert job.estado == EstadoJob.PROCESANDO


def test_marcar_completado_guarda_resultado():
    job = Job(texto="prueba")
    job.marcar_completado("POSITIVO", ["prueba", "bueno"])
    assert job.estado == EstadoJob.COMPLETADO
    assert job.resultado_sentimiento == "POSITIVO"
    assert job.palabras_clave == ["prueba", "bueno"]
