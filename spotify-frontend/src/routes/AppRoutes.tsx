import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import LoginPage from '../pages/LoginPage';
import DashboardPage from '../pages/DashboardPage';
import SearchPage from '../pages/SearchPage';
import ArtistPage from '../pages/ArtistPage';
import AlbumPage from '../pages/AlbumPage';
import ProtectedRoute from '../components/ProtectedRoute'; // Importamos ProtectedRoute
import { AuthProvider } from '../context/AuthContext'; // Importamos el AuthProvider solo para rutas protegidas

const AppRoutes: React.FC = () => {
  return (
    <Router>
      <Routes>
        {/* Ruta pública */}
        <Route path="/" element={<Navigate to="/login" replace />} />
        <Route path="/login" element={<LoginPage />} />

        {/* Rutas protegidas */}
        <Route
          path="/dashboard"
          element={
            <AuthProvider> 
              <ProtectedRoute>
                <DashboardPage />
              </ProtectedRoute>
            </AuthProvider>
          }
        />
        <Route
          path="/search"
          element={
            <AuthProvider> 
              <ProtectedRoute>
                <SearchPage />
              </ProtectedRoute>
            </AuthProvider>
          }
        />
        <Route
          path="/artist/:id"
          element={
            <AuthProvider> 
              <ProtectedRoute>
                <ArtistPage />
              </ProtectedRoute>
            </AuthProvider>
          }
        />
        <Route
          path="/album/:id"
          element={
            <AuthProvider> 
              <ProtectedRoute>
                <AlbumPage />
              </ProtectedRoute>
            </AuthProvider>
          }
        />

        {/* Ruta por defecto (404) */}
        <Route path="*" element={<h1>404 - Página no encontrada</h1>} />
      </Routes>
    </Router>
  );
};

export default AppRoutes;
