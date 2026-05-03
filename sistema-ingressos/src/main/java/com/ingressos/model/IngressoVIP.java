package com.ingressos.model;

/** Ingresso VIP: acréscimo de 50% sobre o preço base. */
public class IngressoVIP extends Ingresso {

    private static final double TAXA_VIP = 1.5;

    public IngressoVIP() { super(); }

    public IngressoVIP(String nomeEvento, String localEvento, String dataEvento,
                       String nomeComprador, String cpfComprador, Double precoBase) {
        super(nomeEvento, localEvento, dataEvento, nomeComprador, cpfComprador, precoBase);
    }

    @Override
    public Double calcularValor() {
        return getPrecoBase() * TAXA_VIP;
    }

    @Override
    public String getTipoIngresso() { return "VIP"; }

    @Override
    public String getDescricaoTipo() { return "Ingresso VIP - Área exclusiva (+50%)"; }
}
