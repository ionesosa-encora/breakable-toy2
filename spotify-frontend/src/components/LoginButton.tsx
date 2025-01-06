import React from 'react';

const LoginButton: React.FC = () => {
  const handleLogin = () => {
    window.location.href = '/api/auth/spotify/login';
  };

  return (
    <button onClick={handleLogin} className="login-button">
      Login with Spotify
    </button>
  );
};

export default LoginButton;
