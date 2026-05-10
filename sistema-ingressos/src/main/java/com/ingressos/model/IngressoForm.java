package com.ingressos.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class IngressoForm {

    @NotBlank private String tipo;
    private String eventoId;
    @NotBlank private String nomeEvento;
    @NotBlank private String localEvento;
    @NotBlank private String dataEvento;
    @NotBlank private String nomeComprador;
    private String cpfComprador;
    @NotNull @Min(0) private Double precoBase;
    private String extra;

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getEventoId() { return eventoId; }
    public void setEventoId(String eventoId) { this.eventoId = eventoId; }
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
    public String getExtra() { return extra; }
    public void setExtra(String extra) { this.extra = extra; }
}
