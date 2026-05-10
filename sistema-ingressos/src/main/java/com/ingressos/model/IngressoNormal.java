package com.ingressos.model;

public class IngressoNormal extends Ingresso {
    @Override public Double calcularValor() { return getPrecoBase(); }
    @Override public String getTipoIngresso() { return "NORMAL"; }
    @Override public String getDescricaoTipo() { return "Ingresso Normal — valor integral"; }
}
