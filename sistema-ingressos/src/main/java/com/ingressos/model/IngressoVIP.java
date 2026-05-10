package com.ingressos.model;

public class IngressoVIP extends Ingresso {
    private String beneficios = "Área VIP, open bar, meet & greet";
    @Override public Double calcularValor() { return getPrecoBase() * 1.5; }
    @Override public String getTipoIngresso() { return "VIP"; }
    @Override public String getDescricaoTipo() { return "Ingresso VIP — +50% sobre o valor base"; }
    public String getBeneficios() { return beneficios; }
    public void setBeneficios(String beneficios) { this.beneficios = beneficios; }
}
