package com.seplagseletivo.projeto_backend.dto;

public class AuthRequestDTO {
    private String username;
    private String password;

    // Getters e Setters (Essenciais para o Controller funcionar)
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}