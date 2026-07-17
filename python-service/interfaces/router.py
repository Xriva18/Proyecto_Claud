# kguanoluisa, Endpoints REST POST /jobs y GET /jobs/{id} con DTOs Pydantic, clases CrearJobDTO JobResponseDTO, 2026-07-17
from fastapi import APIRouter, BackgroundTasks, HTTPException
from pydantic import BaseModel

from application.casos_de_uso import ConsultarEstadoUseCase, CrearSubmissionUseCase

router = APIRouter(prefix="/jobs", tags=["Jobs"])


class CrearJobDTO(BaseModel):
    texto: str


class JobResponseDTO(BaseModel):
    id: str
    texto: str
    estado: str
    resultado_sentimiento: str | None = None
    palabras_clave: list[str] | None = None


class CrearJobResponseDTO(BaseModel):
    id: str
    estado: str


@router.post("/", response_model=CrearJobResponseDTO)
def crear_job(dto: CrearJobDTO, background_tasks: BackgroundTasks):
    try:
        caso_uso = CrearSubmissionUseCase()
        job = caso_uso.ejecutar(dto.texto)
        background_tasks.add_task(
            caso_uso.procesar_en_background,
            job.id,
            job.texto,
        )
        return CrearJobResponseDTO(id=job.id, estado=job.estado.value)
    except ValueError as exc:
        raise HTTPException(status_code=400, detail=str(exc)) from exc


@router.get("/{job_id}", response_model=JobResponseDTO)
def consultar_job(job_id: str):
    try:
        job = ConsultarEstadoUseCase().ejecutar(job_id)
        return JobResponseDTO(
            id=job.id,
            texto=job.texto,
            estado=job.estado.value,
            resultado_sentimiento=job.resultado_sentimiento,
            palabras_clave=job.palabras_clave,
        )
    except LookupError as exc:
        raise HTTPException(status_code=404, detail=str(exc)) from exc
