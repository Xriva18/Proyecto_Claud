# kguanoluisa, Punto de entrada FastAPI con CORS y router de jobs, sin nuevas variables, 2026-07-17
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

from infrastructure.database import init_db
from interfaces.router import router

app = FastAPI(title="Analyticore Submission Service", version="1.0.0")

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

app.include_router(router)


@app.on_event("startup")
def on_startup():
    init_db()


@app.get("/health")
def health():
    return {"status": "ok"}
