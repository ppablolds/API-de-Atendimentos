package dev.atendimentoAPI.atendimento.service;

import dev.atendimentoAPI.atendimento.model.Atendimento;
import dev.atendimentoAPI.atendimento.model.StatusAtendimento;
import dev.atendimentoAPI.atendimento.repository.AtendimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtendimentoService {

    private final AtendimentoRepository atendimentoRepository;

    // Injetando o AtendimentoRepository no construtor
    @Autowired
    public AtendimentoService(AtendimentoRepository atendimentoRepository) {
        this.atendimentoRepository = atendimentoRepository;
    }

    // Metodo responsável por salvar um atendimento no banco.
    public Atendimento salvarAtendimento(Atendimento atendimento) {
        validarStatus(atendimento.getStatus()); // Valida o status
        return atendimentoRepository.save(atendimento);
    }

    // Metodo que retorna todos os atendimentos cadastrados.
    public List<Atendimento> listarAtendimentos() {
        return atendimentoRepository.findAll();
    }

    // Metodo que permite buscar um atendimento pelo ID.
    public Optional<Atendimento> buscarAtendimentoPorId(Long id) {
        return atendimentoRepository.findById(id);
    }

    // Metodo que atualiza um atendimento existente com base no ID.
    public Atendimento atualizarAtendimento(Long id, Atendimento atendimentoAtualizado) {
        if (atendimentoRepository.existsById(id)){
            validarStatus(atendimentoAtualizado.getStatus());
            atendimentoAtualizado.setId(id);
            return atendimentoRepository.save(atendimentoAtualizado);
        }
        return null; // Depois criar uma exceção personalizada
    }

    // Metodo que exclui um atendimento pelo ID.
    public boolean excluirAtendimento(Long id) {
        if (atendimentoRepository.existsById(id)){
            atendimentoRepository.deleteById(id);
            return true;
        }
        return false; // Depois criar uma exceção personalizada
    }

    private void validarStatus(StatusAtendimento status) {
        if (status == null) {
            throw new IllegalArgumentException("Status inválido: não pode ser nulo!");
        }
    }
}
