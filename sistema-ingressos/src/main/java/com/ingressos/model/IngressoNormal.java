package com.ingressos.model;

/** Ingresso Normal: sem desconto, valor igual ao preço base. */
public class IngressoNormal extends Ingresso {

    public IngressoNormal() { super(); }

    public IngressoNormal(String nomeEvento, String localEvento, String dataEvento,
                           String nomeComprador, String cpfComprador, Double precoBase) {
        super(nomeEvento, localEvento, dataEvento, nomeComprador, cpfComprador, precoBase);
    }

    @Override
    public Double calcularValor() {
        return getPrecoBase(); // sem desconto
    }

    @Override
    public String getTipoIngresso() { return "NORMAL"; }

    @Override
    public String getDescricaoTipo() { return "Ingresso Normal - Sem desconto"; }
}
