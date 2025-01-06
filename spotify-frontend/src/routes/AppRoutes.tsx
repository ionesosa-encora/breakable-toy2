import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import LoginPage from '../pages/LoginPage';
import DashboardPage from '../pages/DashboardPage';
import SearchPage from '../pages/SearchPage';
import ArtistPage from '../pages/ArtistPage';
import AlbumPage from '../pages/AlbumPage';
import ProtectedRoute from '../components/ProtectedRoute';
import Header from '../components/Header';
import { AuthProvider } from '../context/AuthContext';

const ProtectedRouteWithHeader: React.FC<{ children: React.ReactNode }> = ({ children }) => (
  <>
    <div className="app-header"> 
      <Header />
    </div>
    <main className="main-container">{children}</main>
  </>
);

const ProtectedRouteWithAuth = ({ element }: { element: React.ReactElement }) => (
  <AuthProvider>
    <ProtectedRoute>{element}</ProtectedRoute>
  </AuthProvider>
);

const AppRoutes: React.FC = () => (
  <Router>
    <Routes>
      <Route path="/" element={<Navigate to="/login" replace />} />
      <Route path="/login" element={<LoginPage />} />
      <Route
        path="/dashboard"
        element={<ProtectedRouteWithAuth element={<ProtectedRouteWithHeader><DashboardPage /></ProtectedRouteWithHeader>} />}
      />
      <Route
        path="/search"
        element={<ProtectedRouteWithAuth element={<ProtectedRouteWithHeader><SearchPage /></ProtectedRouteWithHeader>} />}
      />
      <Route
        path="/artists/:id"
        element={<ProtectedRouteWithAuth element={<ProtectedRouteWithHeader><ArtistPage /></ProtectedRouteWithHeader>} />}
      />
      <Route
        path="/album/:id"
        element={<ProtectedRouteWithAuth element={<ProtectedRouteWithHeader><AlbumPage /></ProtectedRouteWithHeader>} />}
      />
      <Route path="*" element={<h1>404 - Página no encontrada</h1>} />
    </Routes>
  </Router>
);

export default AppRoutes;
