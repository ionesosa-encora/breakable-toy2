import React from 'react';

const LoginButton: React.FC = () => {
  const handleLogin = () => {
    window.location.href = '/auth/spotify/login'; // Redirige al endpoint de login en el backend
  };

  return (
    <button onClick={handleLogin} className="login-button">
      Login with Spotify
    </button>
  );
};

export default LoginButton;
