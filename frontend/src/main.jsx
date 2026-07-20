// kguanoluisa, Punto de entrada React que monta App en el DOM del index.html, sin nuevas variables, 2026-07-17
// kguanoluisa, Funcionamiento: renderiza App dentro de #root con StrictMode para desarrollo, sin nuevas variables, 2026-07-17
import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
)
