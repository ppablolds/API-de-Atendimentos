package dev.atendimentoAPI.atendimento.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Agora a chave secreta vem de um arquivo de propriedades
    @Value("${jwt.secret.key}")
    private String secretKey;

    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hora

    private final Key key;

    public JwtUtil() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // Geração do token com o username (email)
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Extração do username (email) do token
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validação do token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            // O token expirou
            System.out.println("Token expirado");
        } catch (UnsupportedJwtException e) {
            // O token não é suportado
            System.out.println("Token não suportado");
        } catch (MalformedJwtException e) {
            // O token é malformado
            System.out.println("Token malformado");
        } catch (SignatureException e) {
            // A assinatura do token não é válida
            System.out.println("Assinatura inválida");
        } catch (JwtException e) {
            // Erro genérico de JWT
            System.out.println("Erro genérico do JWT");
        }
        return false;
    }
}
