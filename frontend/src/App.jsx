// kguanoluisa, App principal con formulario y polling cada 2s a GET /jobs/{id}, variables jobId job polling, 2026-07-17
// kguanoluisa, Funcionamiento: al enviar texto llama POST /jobs vía jobsApi, guarda el id y consulta GET /jobs/{id} cada 2s hasta COMPLETADO o 30 intentos, sin nuevas variables, 2026-07-17
// kguanoluisa, Layout dual: panel izquierdo para ingreso de texto y panel derecho para estadísticas, sin nuevas variables, 2026-07-21
import { useEffect, useRef, useState } from 'react';
import { consultarJob, crearJob } from './api/jobsApi';
import SubmissionForm from './components/SubmissionForm';
import JobResult from './components/JobResult';

const POLL_INTERVAL_MS = 2000;
const MAX_POLLS = 30;

export default function App() {
  const [texto, setTexto] = useState('');
  const [job, setJob] = useState(null);
  const [jobId, setJobId] = useState(null);
  const [loading, setLoading] = useState(false);
  const [polling, setPolling] = useState(false);
  const [error, setError] = useState('');
  const pollCount = useRef(0);

  useEffect(() => {
    if (!jobId) return undefined;

    pollCount.current = 0;
    setPolling(true);

    const interval = setInterval(async () => {
      try {
        pollCount.current += 1;
        const data = await consultarJob(jobId);
        setJob(data);

        if (data.estado === 'COMPLETADO' || pollCount.current >= MAX_POLLS) {
          setPolling(false);
          clearInterval(interval);
        }
      } catch (err) {
        setError(err.message);
        setPolling(false);
        clearInterval(interval);
      }
    }, POLL_INTERVAL_MS);

    return () => clearInterval(interval);
  }, [jobId]);

  async function handleSubmit() {
    setError('');
    setLoading(true);
    setJob(null);

    try {
      const data = await crearJob(texto);
      setJobId(data.id);
      setJob({ id: data.id, estado: data.estado, texto });
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="app-shell">
      <header className="app-header">
        <h1>Analyticore</h1>
        <p className="subtitle">Análisis de sentimiento y palabras clave en tiempo real</p>
      </header>

      <div className="app-grid">
        <section className="panel panel-input">
          <div className="panel-header">
            <div className="panel-icon" aria-hidden="true">✎</div>
            <div>
              <h2 className="panel-title">Ingreso de texto</h2>
              <p className="panel-desc">Escribe o pega el contenido a analizar</p>
            </div>
          </div>

          <SubmissionForm
            texto={texto}
            setTexto={setTexto}
            onSubmit={handleSubmit}
            loading={loading || polling}
          />

          {error && <p className="error-banner">{error}</p>}
        </section>

        <section className="panel panel-stats">
          <div className="panel-header">
            <div className="panel-icon" aria-hidden="true">◈</div>
            <div>
              <h2 className="panel-title">Estadísticas</h2>
              <p className="panel-desc">Resultados del análisis procesado</p>
            </div>
          </div>

          <JobResult job={job} polling={polling} texto={texto} />
        </section>
      </div>
    </div>
  );
}
