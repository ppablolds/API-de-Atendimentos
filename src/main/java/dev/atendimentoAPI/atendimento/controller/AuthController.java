package dev.atendimentoAPI.atendimento.controller;

import com.fasterxml.jackson.annotation.JsonView;
import dev.atendimentoAPI.atendimento.model.Usuario;
import dev.atendimentoAPI.atendimento.model.Views;
import dev.atendimentoAPI.atendimento.service.UsuarioService;
import dev.atendimentoAPI.atendimento.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UsuarioService usuarioService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    @JsonView(Views.Registro.class)
    public ResponseEntity<Usuario> registrar(@RequestBody @JsonView(Views.Registro.class) Usuario usuario) {
        if (usuario.getEmail() == null || usuario.getEmail().isBlank() && usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null); // Email não pode ser nulo
        }

        // Salva o novo usuário
        Usuario novoUsuario = usuarioService.salvarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @PostMapping("/login")
    @JsonView(Views.Login.class)
    public ResponseEntity<?> login(@RequestBody @JsonView(Views.Login.class) Usuario usuario) {
        try {
            // Alterado para utilizar o email no token
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getPassword()) // Usando email para autenticação
            );

            // Gerar token com o email
            var token = jwtUtil.generateToken(usuario.getEmail()); // Gerando token com email
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } catch (AuthenticationException e) {
            // Melhorar a mensagem de erro
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação: Email ou senha incorretos.");
        }
    }
}