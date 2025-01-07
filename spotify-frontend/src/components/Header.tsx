import React from "react";
import { useNavigate, useLocation } from "react-router-dom";

const Header: React.FC = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const handleLogout = async () => {
    try {
      const response = await fetch("/api/auth/spotify/logout", {
        method: "POST",
        credentials: "include",
      });

      if (response.ok) {
        navigate("/login");
      } else {
        console.error("Error during logout:", response.statusText);
      }
    } catch (error) {
      console.error("Logout failed:", error);
    }
  };

  const goToDashboard = () => {
    navigate("/dashboard");
  };

  return (
    <header className="header">
      <div className="header__left">
        {location.pathname !== "/dashboard" && (
          <button className="header__dashboard" onClick={goToDashboard}>
            Dashboard
          </button>
        )}
      </div>
      <div className="header__title">Spotify App</div>
      <button className="header__logout" onClick={handleLogout}>
        Logout
      </button>
    </header>
  );
};

export default Header;
