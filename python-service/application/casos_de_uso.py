# kguanoluisa, Casos de uso crear submission y consultar estado con procesamiento en background, clases CrearSubmissionUseCase ConsultarEstadoUseCase, 2026-07-17
# kguanoluisa, Funcionamiento: CrearSubmission guarda PENDIENTE y en background marca PROCESANDO y llama Java; ConsultarEstado lee la fila actual, sin nuevas variables, 2026-07-17
import logging

from domain.job import EstadoJob, Job
from infrastructure.java_client import JavaAnalysisClient
from infrastructure.repositorio import JobRepositorio

logger = logging.getLogger(__name__)


class CrearSubmissionUseCase:
    def __init__(self) -> None:
        self.repo = JobRepositorio()
        self.java_client = JavaAnalysisClient()

    def ejecutar(self, texto: str) -> Job:
        if not texto or not texto.strip():
            raise ValueError("El texto no puede estar vacío")

        job = Job(texto=texto.strip())
        self.repo.guardar(job)
        return job

    def procesar_en_background(self, job_id: str, texto: str) -> None:
        try:
            self.repo.actualizar_estado(job_id, EstadoJob.PROCESANDO.value)
            self.java_client.analizar(job_id, texto)
        except Exception as exc:
            logger.error("Error procesando job %s: %s", job_id, exc)


class ConsultarEstadoUseCase:
    def __init__(self) -> None:
        self.repo = JobRepositorio()

    def ejecutar(self, job_id: str) -> Job:
        job = self.repo.buscar_por_id(job_id)
        if not job:
            raise LookupError(f"Job {job_id} no encontrado")
        return job
