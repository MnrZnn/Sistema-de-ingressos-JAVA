package com.ingressos.model;

/** Ingresso Meia-Entrada: 50% de desconto sobre o preço base. */
public class IngressoMeia extends Ingresso {

    private static final double DESCONTO_MEIA = 0.5;

    public IngressoMeia() { super(); }

    public IngressoMeia(String nomeEvento, String localEvento, String dataEvento,
                        String nomeComprador, String cpfComprador, Double precoBase) {
        super(nomeEvento, localEvento, dataEvento, nomeComprador, cpfComprador, precoBase);
    }

    @Override
    public Double calcularValor() {
        return getPrecoBase() * DESCONTO_MEIA;
    }

    @Override
    public String getTipoIngresso() { return "MEIA"; }

    @Override
    public String getDescricaoTipo() { return "Meia-Entrada - 50% de desconto"; }
}
