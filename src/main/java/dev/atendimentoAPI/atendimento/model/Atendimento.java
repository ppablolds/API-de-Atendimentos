package dev.atendimentoAPI.atendimento.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Atendimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String cliente;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dataHora;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private StatusAtendimento status;

    public Atendimento() {}

    public Atendimento(Long id, String cliente, LocalDateTime dataHora, String descricao, StatusAtendimento status) throws IllegalAccessException {
        this.id = id;
        this.cliente = cliente;
        this.dataHora = dataHora;
        this.descricao = descricao;
        this.status = status;
        setStatus(status);
    }

    @PrePersist
    @PreUpdate
    public void prePersist() {
        this.dataHora = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusAtendimento getStatus() {
        return status;
    }

    public void setStatus(StatusAtendimento status) throws IllegalAccessException {
        if (status == null) {
            throw new IllegalAccessException("Status não pode ser nulo!");
        }
        this.status = status;
    }
}
