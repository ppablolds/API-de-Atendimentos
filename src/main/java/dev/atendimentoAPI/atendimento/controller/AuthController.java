package dev.atendimentoAPI.atendimento.controller;

import com.fasterxml.jackson.annotation.JsonView;
import dev.atendimentoAPI.atendimento.model.Usuario;
import dev.atendimentoAPI.atendimento.model.Views;
import dev.atendimentoAPI.atendimento.service.UsuarioService;
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

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UsuarioService usuarioService, AuthenticationManager authenticationManager) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    @JsonView(Views.Registro.class)
    public ResponseEntity<Usuario> registrar(@RequestBody @JsonView(Views.Registro.class) Usuario usuario) {
        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

        Usuario novoUsuario = usuarioService.salvarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @PostMapping("/login")
    @JsonView(Views.Login.class)
    public ResponseEntity<?> login(@RequestBody @JsonView(Views.Login.class) Usuario usuario) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getPassword())
            );

            return ResponseEntity.ok("Autenticação realizada com sucesso!");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação.");
        }
    }
}
