package com.ingressos.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "eventos")
public class Evento {

    @Id
    private String id;

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    @NotBlank
    private String dataHorario;

    @NotBlank
    private String local;

    @NotNull @Min(0)
    private Integer ingressosDisponiveis;

    @NotNull @Min(0)
    private Double valorBase;

    private boolean ativo = true;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getDataHorario() { return dataHorario; }
    public void setDataHorario(String dataHorario) { this.dataHorario = dataHorario; }
    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }
    public Integer getIngressosDisponiveis() { return ingressosDisponiveis; }
    public void setIngressosDisponiveis(Integer ingressosDisponiveis) { this.ingressosDisponiveis = ingressosDisponiveis; }
    public Double getValorBase() { return valorBase; }
    public void setValorBase(Double valorBase) { this.valorBase = valorBase; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
