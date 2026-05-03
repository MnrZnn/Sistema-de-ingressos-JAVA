package com.ingressos.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO para o formulário de cadastro de ingresso.
 * Desacopla o form da entidade persistida.
 */
public class IngressoForm {

    @NotBlank(message = "Tipo é obrigatório")
    private String tipo; // NORMAL, VIP, MEIA

    @NotBlank(message = "Nome do evento é obrigatório")
    private String nomeEvento;

    @NotBlank(message = "Local é obrigatório")
    private String localEvento;

    @NotBlank(message = "Data do evento é obrigatória")
    private String dataEvento;

    @NotBlank(message = "Nome do comprador é obrigatório")
    private String nomeComprador;

    @NotBlank(message = "CPF é obrigatório")
    private String cpfComprador;

    @NotNull(message = "Preço base é obrigatório")
    @Positive(message = "Preço deve ser positivo")
    private Double precoBase;

    // Getters e Setters
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getNomeEvento() { return nomeEvento; }
    public void setNomeEvento(String nomeEvento) { this.nomeEvento = nomeEvento; }

    public String getLocalEvento() { return localEvento; }
    public void setLocalEvento(String localEvento) { this.localEvento = localEvento; }

    public String getDataEvento() { return dataEvento; }
    public void setDataEvento(String dataEvento) { this.dataEvento = dataEvento; }

    public String getNomeComprador() { return nomeComprador; }
    public void setNomeComprador(String nomeComprador) { this.nomeComprador = nomeComprador; }

    public String getCpfComprador() { return cpfComprador; }
    public void setCpfComprador(String cpfComprador) { this.cpfComprador = cpfComprador; }

    public Double getPrecoBase() { return precoBase; }
    public void setPrecoBase(Double precoBase) { this.precoBase = precoBase; }
}
