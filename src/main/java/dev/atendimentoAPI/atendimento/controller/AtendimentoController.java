package dev.atendimentoAPI.atendimento.controller;

import com.fasterxml.jackson.annotation.JsonView;
import dev.atendimentoAPI.atendimento.model.Atendimento;
import dev.atendimentoAPI.atendimento.model.Usuario;
import dev.atendimentoAPI.atendimento.model.Views;
import dev.atendimentoAPI.atendimento.service.AtendimentoService;
import dev.atendimentoAPI.atendimento.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @JsonView(Views.BuscarAtendimento.class)
    public ResponseEntity<Atendimento> criarAtendimento(@RequestBody @JsonView(Views.CriarAtendimento.class)
                                                            Atendimento atendimento) throws IllegalAccessException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Usuario> usuarioOptional = usuarioService.buscarPorEmail(email);

        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Usuario usuario = usuarioOptional.get();

        System.out.println("Status recebido no Controller: " + atendimento.getStatus());

        Atendimento atendimentoCriado = atendimentoService.criarAtendimento(usuario, atendimento);
        return ResponseEntity.status(HttpStatus.CREATED).body(atendimentoCriado);
    }

    // Listar todos os atendimentos
    @GetMapping("/")
    @JsonView(Views.BuscarAtendimento.class)
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
