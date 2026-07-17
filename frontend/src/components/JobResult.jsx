// kguanoluisa, Vista de resultado con polling y estados PENDIENTE PROCESANDO COMPLETADO, prop job, 2026-07-17
export default function JobResult({ job, polling }) {
  if (!job) return null;

  const completado = job.estado === 'COMPLETADO';

  return (
    <div className="result-card">
      <p>
        Estado:{' '}
        <span className={`status ${completado ? 'completado' : ''}`}>
          {job.estado}
        </span>
      </p>

      {polling && !completado && (
        <p className="spinner">Procesando análisis, espera un momento...</p>
      )}

      {completado && (
        <>
          <p>
            <strong>Sentimiento:</strong> {job.resultado_sentimiento}
          </p>
          <p>
            <strong>Palabras clave:</strong>
          </p>
          <ul className="keywords">
            {(job.palabras_clave || []).map((palabra) => (
              <li key={palabra}>{palabra}</li>
            ))}
          </ul>
        </>
      )}
    </div>
  );
}
