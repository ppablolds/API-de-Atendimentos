package dev.atendimentoAPI.atendimento.security;

import dev.atendimentoAPI.atendimento.service.UsuarioServiceImpl;
import dev.atendimentoAPI.atendimento.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UsuarioServiceImpl usuarioService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UsuarioServiceImpl usuarioService) {
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);

            // Verifique a validade do token antes de extrair o username
            if (jwtUtil.validateToken(jwt)) {
                String username = jwtUtil.extractUsername(jwt);

                // Verifique se o username não é nulo e se o usuário não foi autenticado previamente
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Carregue o usuário com base no email (não no username)
                    var userDetails = usuarioService.loadUserByUsername(username);

                    var authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Defina o contexto de segurança com o token de autenticação
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        // Continue o filtro
        filterChain.doFilter(request, response);
    }
}
