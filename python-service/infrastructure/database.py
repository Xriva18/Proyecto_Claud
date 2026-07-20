# kguanoluisa, Configuración SQLAlchemy PostgreSQL y modelo ORM JobModel, variables engine SessionLocal Base, 2026-07-17
# kguanoluisa, Funcionamiento: lee DATABASE_URL, crea engine/sesiones y mapea la tabla jobs compartida con Python y Java, sin nuevas variables, 2026-07-17
import os

from sqlalchemy import Column, DateTime, String, Text, create_engine
from sqlalchemy.orm import declarative_base, sessionmaker
from datetime import datetime

DATABASE_URL = os.getenv(
    "DATABASE_URL",
    "postgresql://analyticore:analyticore@localhost:5432/db_analyticore",
)

engine = create_engine(DATABASE_URL)
SessionLocal = sessionmaker(bind=engine)
Base = declarative_base()


class JobModel(Base):
    __tablename__ = "jobs"

    id = Column(String(36), primary_key=True)
    texto = Column(Text, nullable=False)
    estado = Column(String(20), nullable=False, default="PENDIENTE")
    resultado_sentimiento = Column(String(50), nullable=True)
    palabras_clave = Column(Text, nullable=True)
    fecha_creacion = Column(DateTime, default=datetime.utcnow)
    fecha_actualizacion = Column(DateTime, default=datetime.utcnow)


def init_db() -> None:
    Base.metadata.create_all(engine)
