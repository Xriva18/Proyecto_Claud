// kguanoluisa, Vista de resultado con polling y estados PENDIENTE PROCESANDO COMPLETADO, prop job, 2026-07-17
// kguanoluisa, Funcionamiento: muestra el estado del job y, al completarse, el sentimiento y las palabras clave devueltas por Python, sin nuevas variables, 2026-07-17
// kguanoluisa, Panel de estadísticas con tarjetas métricas y etiquetas de palabras clave, prop texto, 2026-07-21

function getSentimentClass(sentimiento) {
  if (!sentimiento) return '';
  const lower = sentimiento.toLowerCase();
  if (lower.includes('positiv')) return 'sentiment-positive';
  if (lower.includes('negativ')) return 'sentiment-negative';
  return 'sentiment-neutral';
}

function countWords(texto) {
  if (!texto || !texto.trim()) return 0;
  return texto.trim().split(/\s+/).length;
}

export default function JobResult({ job, polling, texto }) {
  if (!job) {
    return (
      <div className="stats-empty">
        <div className="stats-empty-icon" aria-hidden="true">📊</div>
        <p>
          Envía un texto para ver aquí el sentimiento, las palabras clave y las métricas del análisis.
        </p>
      </div>
    );
  }

  const completado = job.estado === 'COMPLETADO';
  const palabrasClave = job.palabras_clave || [];
  const wordCount = countWords(job.texto || texto);

  if (polling && !completado) {
    return (
      <div className="spinner-block">
        <div className="spinner-ring" />
        <p className="spinner-text">Procesando análisis, espera un momento...</p>
        <span className="status-badge">
          <span className="status-dot" />
          {job.estado}
        </span>
      </div>
    );
  }

  return (
    <>
      <div className="stats-grid">
        <div className="stat-card">
          <p className="stat-label">Estado</p>
          <span className={`status-badge ${completado ? 'completado' : ''}`}>
            {!completado && <span className="status-dot" />}
            {job.estado}
          </span>
        </div>

        <div className="stat-card">
          <p className="stat-label">Palabras</p>
          <p className="stat-value">{wordCount}</p>
        </div>

        {completado && (
          <>
            <div className="stat-card">
              <p className="stat-label">Sentimiento</p>
              <p className={`stat-value ${getSentimentClass(job.resultado_sentimiento)}`}>
                {job.resultado_sentimiento}
              </p>
            </div>

            <div className="stat-card">
              <p className="stat-label">Palabras clave</p>
              <p className="stat-value">{palabrasClave.length}</p>
            </div>
          </>
        )}
      </div>

      {completado && palabrasClave.length > 0 && (
        <div className="keywords-section">
          <p className="keywords-title">Palabras clave detectadas</p>
          <ul className="keywords-tags">
            {palabrasClave.map((palabra) => (
              <li key={palabra} className="keyword-tag">
                {palabra}
              </li>
            ))}
          </ul>
        </div>
      )}

      {completado && palabrasClave.length === 0 && (
        <div className="stats-empty" style={{ flex: 0, padding: '16px 0' }}>
          <p>No se detectaron palabras clave en el texto analizado.</p>
        </div>
      )}
    </>
  );
}
