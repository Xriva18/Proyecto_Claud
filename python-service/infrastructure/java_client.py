# kguanoluisa, Cliente HTTP para llamar al servicio Java POST /analysis, clase JavaAnalysisClient, 2026-07-17
import os

import httpx


class JavaAnalysisClient:
    def __init__(self) -> None:
        self.base_url = os.getenv("JAVA_SERVICE_URL", "http://localhost:8080")

    def analizar(self, job_id: str, texto: str) -> dict:
        url = f"{self.base_url}/analysis"
        payload = {"jobId": job_id, "texto": texto}
        with httpx.Client(timeout=30.0) as client:
            response = client.post(url, json=payload)
            response.raise_for_status()
            return response.json()
