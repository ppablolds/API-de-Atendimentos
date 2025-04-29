package dev.atendimentoAPI.atendimento.model;

public enum StatusAtendimento {
    ABERTO("Aberto"),
    EM_ANDAMENTO("Em andamento"),
    FINALIZADO("Finalizado"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusAtendimento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    // Metodo para validar o status
    public static boolean isValidStatus(String status) {
        for (StatusAtendimento s : StatusAtendimento.values()) {
            if (s.name().equalsIgnoreCase(status)) {
                return true;
            }
        }
        return false;
    }
}
