package com.ingressos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

/**
 * Classe abstrata base para todos os tipos de ingresso.
 * Polimorfismo: cada subclasse implementa calcularValor() diferente.
 */
@Document(collection = "ingressos")
public abstract class Ingresso {

    @Id
    private String id;

    private String nomeEvento;
    private String localEvento;
    private String dataEvento;
    private String nomeComprador;
    private String cpfComprador;
    private Double precoBase;
    private String status; // ATIVO, UTILIZADO, CANCELADO
    private LocalDateTime dataCadastro;

    public Ingresso() {
        this.status = "ATIVO";
        this.dataCadastro = LocalDateTime.now();
    }

    public Ingresso(String nomeEvento, String localEvento, String dataEvento,
                    String nomeComprador, String cpfComprador, Double precoBase) {
        this();
        this.nomeEvento = nomeEvento;
        this.localEvento = localEvento;
        this.dataEvento = dataEvento;
        this.nomeComprador = nomeComprador;
        this.cpfComprador = cpfComprador;
        this.precoBase = precoBase;
    }

    // Métodos abstratos — cada tipo implementa sua lógica
    public abstract Double calcularValor();
    public abstract String getTipoIngresso();
    public abstract String getDescricaoTipo();

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

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

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDateTime dataCadastro) { this.dataCadastro = dataCadastro; }
}
