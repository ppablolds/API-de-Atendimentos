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
    // @Value("${jwt.secret.key}")
    // private String SECRET_KEY;

    private final String SECRET_KEY = "dc45fbdbc4e58d8e339de032b60ea9c19d9584ff40700e580cdaedeeb805133c417311d760ab7b257e96284a0c25852bb7e6c24421826d2783be125c9089dc36b25e2c713768d916d8740ce1280aa0ec82541256deb9190596417da520fa612ad8031f4a8304866220a80f6d49afdff8126087f4db591834a7a690a1bae7dd84023154f120098bea62a7190a9cba4470e4f86fd56ea56fd911de23b3c554a63c89d26a0b0b6fd225d36f0f6b1a0e2f371b20a9630d40e32e98cc8c5e7b60fba1dfa959a52abdafb5a3a189108df4bbd740407a1c96b448b2fdf718881017809bb37742d323fee5a993e3528aaff068c486730e0a54614bbb05e1fc9ec71db6ed";

    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hora

    private final Key key;

    public JwtUtil() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
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
