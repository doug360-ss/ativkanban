package com.trabalho.kanban.controller;

import com.trabalho.kanban.config.configure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private configure jwtUtil;

    // Endpoint para login, agora retorna o AuthResponse com token e username
    @PostMapping("/login")
    public AuthRequest login(@RequestBody AuthRequest authRequest) throws CustomAuthenticationException {
        try {
            // Autenticando o usuário
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            // Gerando o token para o usuário
            String token = jwtUtil.generateToken(authRequest.getUsername());

            // Retornando o AuthResponse com token e nome de usuário
            return new AuthRequest(token, authRequest.getUsername());
        } catch (AuthenticationException e) {
            throw new CustomAuthenticationException();
        }
    }

    private class CustomAuthenticationException extends Exception {
    }
}
