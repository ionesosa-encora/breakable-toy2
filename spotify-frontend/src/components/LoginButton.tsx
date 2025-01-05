import React from "react";

const LoginButton: React.FC = () => {
  const handleLogin = async () => {
    try {
      // Llamar al backend para iniciar el login
      const response = await fetch("http://localhost:8080/auth/spotify/login", {
        method: "GET",
        redirect: "follow", 
      });

      if (response.redirected) {
        window.location.href = response.url; // Asegurarse de redirigir al usuario
      }
    } catch (error) {
      console.error("Error during login process:", error);
    }
  };

  return (
    <button onClick={handleLogin} className="login-button">
      Login to Spotify
    </button>
  );
};

export default LoginButton;
