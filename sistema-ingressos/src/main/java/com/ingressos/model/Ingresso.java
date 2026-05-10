package com.ingressos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "ingressos")
public abstract class Ingresso {

    @Id
    private String id;

    private String eventoId;
    private String nomeEvento;
    private String localEvento;
    private String dataEvento;
    private String nomeComprador;
    private String cpfComprador;
    private String usuarioId;
    private Double precoBase;
    private String status; // RESERVADO, CONFIRMADO, UTILIZADO, CANCELADO
    private String qrCodeBase64;
    private LocalDateTime dataCadastro;

    public Ingresso() {
        this.status = "RESERVADO";
        this.dataCadastro = LocalDateTime.now();
    }

    public abstract Double calcularValor();
    public abstract String getTipoIngresso();
    public abstract String getDescricaoTipo();

    public String imprimirIngresso() {
        return String.format("[%s] %s\nEvento: %s | Local: %s | Data: %s\nComprador: %s\nValor: R$ %.2f | Status: %s",
                getTipoIngresso(), id, nomeEvento, localEvento, dataEvento, nomeComprador, calcularValor(), status);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
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
    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
    public Double getPrecoBase() { return precoBase; }
    public void setPrecoBase(Double precoBase) { this.precoBase = precoBase; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getQrCodeBase64() { return qrCodeBase64; }
    public void setQrCodeBase64(String qrCodeBase64) { this.qrCodeBase64 = qrCodeBase64; }
    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDateTime dataCadastro) { this.dataCadastro = dataCadastro; }
}
