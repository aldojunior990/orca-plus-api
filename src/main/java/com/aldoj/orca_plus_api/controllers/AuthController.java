package com.aldoj.orca_plus_api.controllers;

import com.aldoj.orca_plus_api.configs.TokenService;
import com.aldoj.orca_plus_api.domain.user.AuthResponseDTO;
import com.aldoj.orca_plus_api.domain.user.User;
import com.aldoj.orca_plus_api.domain.user.UserLoginDTO;
import com.aldoj.orca_plus_api.domain.user.UserRegisterDTO;
import com.aldoj.orca_plus_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login") // Rota de registro na aplicação
    public ResponseEntity<AuthResponseDTO> login(@RequestBody UserLoginDTO data) {
        var authenticate = new UsernamePasswordAuthenticationToken(data.email(), data.password()); // Senha e Email em formato HASH
        var auth = this.authenticationManager.authenticate(authenticate); // Realiza a autenticação

        var token = tokenService.generateToken((User) auth.getPrincipal()); // Gera o token

        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody UserRegisterDTO data) {
        if (this.userRepository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();
        var encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        var user = new User(data.name(), data.email(), encryptedPassword, data.address(), data.contact());
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}
