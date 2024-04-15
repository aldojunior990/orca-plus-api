package com.aldoj.orca_plus_api.configs;

import com.aldoj.orca_plus_api.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository repository;

    @Override // Aplica filtro que recupera o token e
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        if (token != null) {
            var email = tokenService.validateToken(token); // Valida o token e pega o email referente ao token
            UserDetails user = repository.findByEmail(email); // Recupera o usuario do token

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()); // Autentica o usuario e atribui suas autoridades
            SecurityContextHolder.getContext().setAuthentication(authentication);  // Salva o usuario autenticado no contexto de segurança
        }
        filterChain.doFilter(request, response);  // Passa para o proximo filtro sem salvar nada no contexto
    }

    public String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization"); // Pega o campo authorization da requsição HTTP
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", ""); // Recupera o valor do Token tirando o Bearer padrão.
    }
}