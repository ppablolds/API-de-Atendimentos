package dev.atendimentoAPI.atendimento.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "atendimentos")
public class Atendimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonView(Views.BuscarAtendimento.class)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonView(Views.BuscarAtendimento.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Usuario usuario; // Relacionamento com o usuário (quem criou o atendimento)

    @Column(nullable = false)
    @JsonView({Views.CriarAtendimento.class, Views.BuscarAtendimento.class})
    private String descricao;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    //@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_hora", nullable = false)
    private LocalDate dataAtendimento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true )
    @JsonView(Views.BuscarAtendimento.class)
    //@JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private StatusAtendimento status;

    public Atendimento() {}

    public Atendimento(Usuario usuario, LocalDate dataAtendimento, String descricao, StatusAtendimento status) throws IllegalAccessException {
        this.usuario = usuario;
        this.dataAtendimento = dataAtendimento;
        this.descricao = descricao;
        this.status = status;
    }

    public Long getId() {
        return id;
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

    public void setStatus(StatusAtendimento status) {
        if (status == null) {
            throw new IllegalArgumentException("Status não pode ser nulo!");
        }
        this.status = status;
    }
}
