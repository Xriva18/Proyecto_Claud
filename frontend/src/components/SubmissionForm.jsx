// kguanoluisa, Formulario para enviar texto a analizar, variables texto setTexto onSubmit loading, 2026-07-17
export default function SubmissionForm({ texto, setTexto, onSubmit, loading }) {
  return (
    <form
      onSubmit={(e) => {
        e.preventDefault();
        onSubmit();
      }}
    >
      <label htmlFor="texto">Texto a analizar</label>
      <textarea
        id="texto"
        value={texto}
        onChange={(e) => setTexto(e.target.value)}
        placeholder="Escribe un texto para analizar sentimiento y palabras clave..."
        disabled={loading}
      />
      <button type="submit" disabled={loading || !texto.trim()}>
        {loading ? 'Enviando...' : 'Analizar'}
      </button>
    </form>
  );
}
