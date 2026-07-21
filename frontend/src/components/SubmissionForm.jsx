// kguanoluisa, Formulario para enviar texto a analizar, variables texto setTexto onSubmit loading, 2026-07-17
// kguanoluisa, Funcionamiento: captura el texto del usuario y dispara onSubmit hacia App, que crea el job en Python, sin nuevas variables, 2026-07-17
// kguanoluisa, Mejora UX con contador de caracteres y estilos del panel de ingreso, sin nuevas variables, 2026-07-21
export default function SubmissionForm({ texto, setTexto, onSubmit, loading }) {
  return (
    <form
      className="submission-form"
      onSubmit={(e) => {
        e.preventDefault();
        onSubmit();
      }}
    >
      <label htmlFor="texto" className="form-label">
        Texto a analizar
      </label>

      <div className="textarea-wrapper">
        <textarea
          id="texto"
          value={texto}
          onChange={(e) => setTexto(e.target.value)}
          placeholder="Escribe un texto para analizar sentimiento y palabras clave..."
          disabled={loading}
        />
        <span className="char-count">{texto.length} caracteres</span>
      </div>

      <button type="submit" className="btn-analyze" disabled={loading || !texto.trim()}>
        {loading ? (
          <>
            <span className="spinner-ring" style={{ width: 18, height: 18, borderWidth: 2 }} />
            Procesando...
          </>
        ) : (
          'Analizar texto'
        )}
      </button>
    </form>
  );
}
