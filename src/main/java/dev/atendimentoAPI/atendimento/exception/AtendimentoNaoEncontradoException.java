package dev.atendimentoAPI.atendimento.exception;

public class AtendimentoNaoEncontradoException extends RuntimeException {
    public AtendimentoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
