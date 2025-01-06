import React from "react";
import { useNavigate } from "react-router-dom";

const Header: React.FC = () => {
  const navigate = useNavigate();

  const handleLogout = async () => {
    try {
      // Hacer una petición al endpoint de logout del backend
      const response = await fetch("/api/auth/spotify/logout", {
        method: "POST",
        credentials: "include", // Incluir cookies
      });

      if (response.ok) {
        // Redirigir al login después del logout
        navigate("/login");
      } else {
        console.error("Error during logout:", response.statusText);
      }
    } catch (error) {
      console.error("Logout failed:", error);
    }
  };

  return (
    <header className="header">
      <div className="header__title">Spotify App</div>
      <button className="header__logout" onClick={handleLogout}>
        Logout
      </button>
    </header>
  );
};

export default Header;
