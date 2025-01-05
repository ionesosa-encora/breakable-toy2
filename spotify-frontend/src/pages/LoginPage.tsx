// src/pages/LoginPage.tsx
import React from "react";
import LoginButton from "../components/LoginButton";

const LoginPage: React.FC = () => {
  return (
    <div style={{ display: "flex", justifyContent: "center", alignItems: "center", height: "100vh", flexDirection: "column", border: "1px solid blue" }}>
      <h1>Login to Spotify</h1>
      <LoginButton />
    </div>
  );
};

export default LoginPage;
