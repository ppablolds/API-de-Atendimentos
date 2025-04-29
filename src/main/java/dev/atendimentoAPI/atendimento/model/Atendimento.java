package dev.atendimentoAPI.atendimento.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Atendimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cliente;
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    private StatusAtendimento status;

    public Atendimento() {}

    public Atendimento(Long id, String cliente, LocalDateTime dataHora, StatusAtendimento status) throws IllegalAccessException {
        this.id = id;
        this.cliente = cliente;
        this.dataHora = dataHora;
        this.status = status;
        setStatus(status);
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

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
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
