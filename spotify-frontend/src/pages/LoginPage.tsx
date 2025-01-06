// src/pages/LoginPage.tsx
import React from "react";
import LoginButton from "../components/LoginButton";

const LoginPage: React.FC = () => {
  return (
    <div className="login-page">
      <h1 className="login-page__title">Welcome to Spotify API App</h1>
      <p className="login-page__subtitle">Log in to explore your favorite artists, albums, and tracks.</p>
      <LoginButton />
    </div>
  );
};

export default LoginPage;
