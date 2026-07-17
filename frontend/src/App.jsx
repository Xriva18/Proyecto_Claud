// kguanoluisa, App principal con formulario y polling cada 2s a GET /jobs/{id}, variables jobId job polling, 2026-07-17
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
    <div className="container">
      <h1>Analyticore</h1>
      <p className="subtitle">Análisis de sentimiento y palabras clave</p>

      <SubmissionForm
        texto={texto}
        setTexto={setTexto}
        onSubmit={handleSubmit}
        loading={loading || polling}
      />

      {error && <p className="error">{error}</p>}

      <JobResult job={job} polling={polling} />
    </div>
  );
}
