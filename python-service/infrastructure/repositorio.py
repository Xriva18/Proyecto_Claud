# kguanoluisa, Repositorio PostgreSQL para jobs con mapeo ORM-dominio, clase JobRepositorio, 2026-07-17
# kguanoluisa, Funcionamiento: traduce entre Job (dominio) y JobModel (SQLAlchemy) para guardar, buscar y actualizar estado, sin nuevas variables, 2026-07-17
import json

from domain.job import EstadoJob, Job
from infrastructure.database import JobModel, SessionLocal


class JobRepositorio:
    def guardar(self, job: Job) -> Job:
        session = SessionLocal()
        try:
            model = JobModel(
                id=job.id,
                texto=job.texto,
                estado=job.estado.value,
                resultado_sentimiento=job.resultado_sentimiento,
                palabras_clave=json.dumps(job.palabras_clave) if job.palabras_clave else None,
                fecha_creacion=job.fecha_creacion,
                fecha_actualizacion=job.fecha_actualizacion,
            )
            session.add(model)
            session.commit()
            return job
        finally:
            session.close()

    def buscar_por_id(self, job_id: str) -> Job | None:
        session = SessionLocal()
        try:
            model = session.query(JobModel).filter(JobModel.id == job_id).first()
            if not model:
                return None
            return self._to_domain(model)
        finally:
            session.close()

    def actualizar_estado(self, job_id: str, estado: str) -> None:
        session = SessionLocal()
        try:
            model = session.query(JobModel).filter(JobModel.id == job_id).first()
            if model:
                model.estado = estado
                session.commit()
        finally:
            session.close()

    def _to_domain(self, model: JobModel) -> Job:
        palabras = json.loads(model.palabras_clave) if model.palabras_clave else None
        return Job(
            id=model.id,
            texto=model.texto,
            estado=EstadoJob(model.estado),
            resultado_sentimiento=model.resultado_sentimiento,
            palabras_clave=palabras,
            fecha_creacion=model.fecha_creacion,
            fecha_actualizacion=model.fecha_actualizacion,
        )
