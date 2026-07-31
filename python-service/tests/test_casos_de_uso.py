# kguanoluisa, Pruebas unitarias de CrearSubmissionUseCase con mock del repositorio, sin nuevas variables, 2026-07-30
import pytest
from unittest.mock import patch

from application.casos_de_uso import CrearSubmissionUseCase
from domain.job import EstadoJob


def test_ejecutar_lanza_error_si_texto_vacio():
    use_case = CrearSubmissionUseCase()
    with pytest.raises(ValueError):
        use_case.ejecutar("   ")


@patch("application.casos_de_uso.JobRepositorio.guardar")
def test_ejecutar_crea_job_pendiente(mock_guardar):
    mock_guardar.return_value = None
    use_case = CrearSubmissionUseCase()

    job = use_case.ejecutar("Este es un texto de prueba")

    assert job.estado == EstadoJob.PENDIENTE
    assert job.texto == "Este es un texto de prueba"
    mock_guardar.assert_called_once()
