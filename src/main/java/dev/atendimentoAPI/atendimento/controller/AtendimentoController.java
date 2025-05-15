package dev.atendimentoAPI.atendimento.controller;

import dev.atendimentoAPI.atendimento.model.Atendimento;
import dev.atendimentoAPI.atendimento.model.Usuario;
import dev.atendimentoAPI.atendimento.service.AtendimentoService;
import dev.atendimentoAPI.atendimento.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/atendimentos")
public class AtendimentoController {

    @Autowired
    private AtendimentoService atendimentoService;

    @Autowired
    private UsuarioService usuarioService;

    // Criar um novo atendimento
    @PostMapping("/criar-atendimento")
    public ResponseEntity<Atendimento> criarAtendimento(@RequestBody Atendimento atendimento) throws IllegalAccessException {
        // Recupera o email do usuário autenticado
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // Busca o usuário no banco
        Optional<Usuario> usuario = usuarioService.buscarPorEmail(email);

        Atendimento atendimentoCriado = atendimentoService.criarAtendimento(usuario.get().getId(), atendimento);
        return ResponseEntity.status(HttpStatus.CREATED).body(atendimentoCriado);
    }

    // Listar todos os atendimentos
    @GetMapping("/")
    public ResponseEntity<List<Atendimento>> listarAtendimentos() {
        List<Atendimento> atendimentos = atendimentoService.listarAtendimentos();
        return new ResponseEntity<>(atendimentos, HttpStatus.OK);
    }

    // Buscar um atendimento pelo ID
    @GetMapping("/buscar-atendimento/{id}")
    public ResponseEntity<Atendimento> buscarAtendimento(@PathVariable Long id) {
        Optional<Atendimento> atendimento = atendimentoService.buscarAtendimentoPorId(id);
        if (atendimento.isPresent()){
            return new ResponseEntity<>(atendimento.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Atualizar um atendimento
    @PutMapping("/atualizar-atendimento/{id}")
    public ResponseEntity<Atendimento> atualizarAtendimento(@PathVariable Long id, @RequestBody Atendimento atendimentoAtalizado) throws IllegalAccessException {
        Atendimento atendimento = atendimentoService.atualizarAtendimento(id, atendimentoAtalizado);
        if (atendimento != null) {
            return new ResponseEntity<>(atendimento, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Excluir um atendimento
    @DeleteMapping("/deletar-atendimento/{id}")
    public ResponseEntity<Void> excluirAtendimento(@PathVariable Long id) {
        boolean excluido = atendimentoService.excluirAtendimento(id);

        if (excluido) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
