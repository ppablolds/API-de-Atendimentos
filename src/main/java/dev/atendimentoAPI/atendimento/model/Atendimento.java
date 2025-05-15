package dev.atendimentoAPI.atendimento.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Atendimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private Usuario usuario; // Relacionamento com o usuário (quem criou o atendimento)

    @Column(nullable = false)
    private String descricao;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    //@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_hora", nullable = false)
    private LocalDate dataAtendimento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true )
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private StatusAtendimento status;

    public Atendimento() {}

    public Atendimento(Long id, Usuario usuario, LocalDate dataAtendimento, String descricao, StatusAtendimento status) throws IllegalAccessException {
        this.id = id;
        this.usuario = usuario;
        this.dataAtendimento = dataAtendimento;
        this.descricao = descricao;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getDataAtendimento() {
        return dataAtendimento;
    }

    public void setDataAtendimento(LocalDate dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

    @PreUpdate
    public void preUpdate() {
        this.dataAtendimento = LocalDate.now();
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
