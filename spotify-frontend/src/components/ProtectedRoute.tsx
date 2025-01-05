import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuthContext } from '../context/AuthContext';

interface ProtectedRouteProps {
  children: React.ReactElement;
}

const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ children }) => {
  const { uuid } = useAuthContext();

  // Si no hay un UUID válido, redirige al login
  console.log("protectedRoute uuid:", uuid)
  if (!uuid) {
    console.log("no hay uuid en protection route")
    return <Navigate to="/login" replace />;
    
  }

  console.log("si hay uuid", uuid, "y se retornara el hijo", children)
  // Si el usuario está autenticado, renderiza el contenido protegido
  return children;
};

export default ProtectedRoute;
