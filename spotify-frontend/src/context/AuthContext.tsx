import React, { createContext, useContext, useState, useEffect, ReactNode } from 'react';
import { getCookie } from '../utils/cookies'; // Importar la función para leer cookies

// Interfaz para definir los valores que manejará el AuthContext
interface AuthContextType {
    uuid: string | null; // UUID del usuario
    setUUID: (uuid: string | null) => void; // Función para actualizar el UUID
}

// Creamos el contexto
const AuthContext = createContext<AuthContextType | undefined>(undefined);

// Hook personalizado para consumir el contexto de autenticación
export const useAuthContext = (): AuthContextType => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuthContext debe ser usado dentro de un AuthProvider');
    }
    return context;
};

// Proveedor del contexto de autenticación
export const AuthProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [uuid, setUUID] = useState<string | null>(() => {
      const cookieUuid = getCookie('sessionUUID');
      return cookieUuid && cookieUuid.trim() !== '' ? cookieUuid : null;
  });
    // Leer la cookie y establecer el UUID al montar el componente
    useEffect(() => {
        const storedUuid = getCookie('sessionUUID'); // Leer la cookie usando la utilidad
        console.log('Cookie leída desde AuthProvider:', storedUuid); // Debug para verificar la cookie
        if (storedUuid) {
            console.log("aqui se esta seteando la uuid")
            setUUID(storedUuid); // Establecer el UUID en el estado del contexto
        }
        console.log('uuid supuesta almacenada', uuid)
    }, []); // Solo se ejecuta una vez, al montar

    return (
        <AuthContext.Provider value={{ uuid, setUUID }}>
            {children}
        </AuthContext.Provider>
    );
};
