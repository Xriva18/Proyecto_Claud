# kguanoluisa, Entidad Job y enum EstadoJob para el servicio de submisión, variables EstadoJob, 2026-07-17
from dataclasses import dataclass, field
from datetime import datetime
from enum import Enum
from uuid import uuid4


class EstadoJob(str, Enum):
    PENDIENTE = "PENDIENTE"
    PROCESANDO = "PROCESANDO"
    COMPLETADO = "COMPLETADO"


@dataclass
class Job:
    texto: str
    id: str = field(default_factory=lambda: str(uuid4()))
    estado: EstadoJob = EstadoJob.PENDIENTE
    resultado_sentimiento: str | None = None
    palabras_clave: list[str] | None = None
    fecha_creacion: datetime = field(default_factory=datetime.utcnow)
    fecha_actualizacion: datetime = field(default_factory=datetime.utcnow)

    def marcar_procesando(self) -> None:
        self.estado = EstadoJob.PROCESANDO
        self.fecha_actualizacion = datetime.utcnow()

    def marcar_completado(self, sentimiento: str, palabras_clave: list[str]) -> None:
        self.estado = EstadoJob.COMPLETADO
        self.resultado_sentimiento = sentimiento
        self.palabras_clave = palabras_clave
        self.fecha_actualizacion = datetime.utcnow()
