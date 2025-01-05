import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/auth': {
        target: 'http://localhost:8080', // Redirige las rutas de autenticación al backend
        changeOrigin: true,
        secure: false,
      },
      '/spotify': {
        target: 'http://localhost:8080', // Redirige las rutas relacionadas con Spotify al backend
        changeOrigin: true,
        secure: false,
      },
      '/artists': {
        target: 'http://localhost:8080', // Redirige las rutas de artistas
        changeOrigin: true,
        secure: false,
      },
      '/albums': {
        target: 'http://localhost:8080', // Redirige las rutas de álbumes
        changeOrigin: true,
        secure: false,
      },
      '/search': {
        target: 'http://localhost:8080', // Redirige las búsquedas
        changeOrigin: true,
        secure: false,
      },
    },
  },
});
