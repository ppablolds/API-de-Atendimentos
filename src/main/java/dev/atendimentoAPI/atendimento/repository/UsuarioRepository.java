package dev.atendimentoAPI.atendimento.repository;

import dev.atendimentoAPI.atendimento.model.Usuario;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    default Usuario findId(Long id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }
}