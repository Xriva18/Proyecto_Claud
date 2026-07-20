// kguanoluisa, Configuración de Vite para compilar React; en Render el build usa VITE_API_URL del Dockerfile, sin nuevas variables, 2026-07-17
import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
})
