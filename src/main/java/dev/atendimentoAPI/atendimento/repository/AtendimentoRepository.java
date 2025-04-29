package dev.atendimentoAPI.atendimento.repository;

import dev.atendimentoAPI.atendimento.model.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {}
