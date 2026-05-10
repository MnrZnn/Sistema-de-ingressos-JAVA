package com.ingressos.model;

public class IngressoMeia extends Ingresso {
    private String documentoComprobatorio;
    @Override public Double calcularValor() { return getPrecoBase() * 0.5; }
    @Override public String getTipoIngresso() { return "MEIA"; }
    @Override public String getDescricaoTipo() { return "Meia-Entrada — 50% de desconto"; }
    public String getDocumentoComprobatorio() { return documentoComprobatorio; }
    public void setDocumentoComprobatorio(String doc) { this.documentoComprobatorio = doc; }
}
