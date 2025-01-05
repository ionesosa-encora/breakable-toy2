import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuthContext } from '../context/AuthContext'; // Usamos el hook personalizado

const DashboardPage: React.FC = () => {
  const { uuid } = useAuthContext(); // Accedemos al UUID desde el contexto
  const navigate = useNavigate();
  console.log("!!!!! dashboard")
  // Si no hay UUID, redirige al login
  useEffect(() => {
    if (!uuid) {
      navigate('/login'); // Redirige al login si no hay sesión
    }
  }, [uuid, navigate]);
  console.log("uuid en dashboard:", uuid)
  return (
    <div>
      <h1>Welcome to your Dashboard!</h1>
      {/* Aquí puedes añadir el contenido adicional del dashboard */}
    </div>
    
  );
};

export default DashboardPage;
