import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuthContext } from '../context/AuthContext';

interface ProtectedRouteProps {
  children: React.ReactElement;
}

const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ children }) => {
  const { uuid } = useAuthContext();

  // Si no hay un UUID válido, redirige al login
  if (!uuid) {
    return <Navigate to="/login" replace />;
    
  }

  // Si el usuario está autenticado, renderiza el contenido protegido
  return children;
};

export default ProtectedRoute;
