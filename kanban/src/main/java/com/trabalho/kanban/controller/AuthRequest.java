package com.trabalho.kanban.controller;

public class AuthRequest {
    private String token;
    private String username;
    private String password;

    // Construtor
    public AuthRequest(String token, String username) {
        this.token = token;
        this.username = username;
    }

    // Getters e Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
