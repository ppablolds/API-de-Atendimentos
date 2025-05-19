package dev.atendimentoAPI.atendimento.service;

import dev.atendimentoAPI.atendimento.model.Atendimento;
import dev.atendimentoAPI.atendimento.model.StatusAtendimento;
import dev.atendimentoAPI.atendimento.model.Usuario;
import dev.atendimentoAPI.atendimento.repository.AtendimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AtendimentoService {

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Autowired
    private UsuarioService usuarioService;

    // Injetando o AtendimentoRepository no construtor
    public AtendimentoService(AtendimentoRepository atendimentoRepository) {
        this.atendimentoRepository = atendimentoRepository;
    }

    // Metodo responsável por salvar um atendimento no banco.
    public Atendimento criarAtendimento(Usuario usuario, Atendimento atendimento) throws IllegalAccessException {
        atendimento.setUsuario(usuario);
        atendimento.setDataAtendimento(LocalDate.now());
        System.out.println("Status antes de setar no Service: " + atendimento.getStatus());
        atendimento.setStatus(StatusAtendimento.ABERTO);
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
    public Atendimento atualizarAtendimento(Long id, Atendimento atendimentoAtualizado) throws IllegalAccessException {
        return atendimentoRepository.findById(id).map(atendimentoExistente -> {
            atendimentoExistente.setStatus(StatusAtendimento.EM_ANDAMENTO);
            atendimentoExistente.setDataAtendimento(LocalDate.now()); // se quiser atualizar a data
            atendimentoExistente.preUpdate();

            return atendimentoRepository.save(atendimentoExistente);
        }).orElse(null); // ou lançar exceção personalizada
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
