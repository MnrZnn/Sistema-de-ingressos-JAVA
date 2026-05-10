package com.ingressos.service;

import com.ingressos.model.*;
import com.ingressos.repository.IngressoRepository;
import com.ingressos.utils.QrCodeUtils;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class IngressoService {

    private final IngressoRepository repo;
    private final EventoService eventoService;

    public IngressoService(IngressoRepository repo, EventoService eventoService) {
        this.repo = repo;
        this.eventoService = eventoService;
    }

    public Ingresso reservar(IngressoForm form, String usuarioId) {
        Evento evento = eventoService.buscarPorId(form.getEventoId())
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
        if (evento.getIngressosDisponiveis() <= 0)
            throw new RuntimeException("Não há ingressos disponíveis");

        Ingresso ing = fabricar(form.getTipo());
        ing.setEventoId(evento.getId());
        ing.setNomeEvento(evento.getNome());
        ing.setLocalEvento(evento.getLocal());
        ing.setDataEvento(evento.getDataHorario());
        ing.setNomeComprador(form.getNomeComprador());
        ing.setCpfComprador(form.getCpfComprador());
        ing.setPrecoBase(evento.getValorBase());
        ing.setUsuarioId(usuarioId);

        if (ing instanceof IngressoVIP vip && form.getExtra() != null) vip.setBeneficios(form.getExtra());
        if (ing instanceof IngressoMeia meia && form.getExtra() != null) meia.setDocumentoComprobatorio(form.getExtra());

        Ingresso salvo = repo.save(ing);
        salvo.setQrCodeBase64(QrCodeUtils.gerarBase64("INGRESSO:" + salvo.getId()));
        salvo = repo.save(salvo);
        eventoService.decrementar(evento.getId());
        return salvo;
    }

    /** Cadastro administrativo direto (sem evento) */
    public Ingresso criar(IngressoForm form) {
        Ingresso ing = fabricar(form.getTipo());
        ing.setNomeEvento(form.getNomeEvento());
        ing.setLocalEvento(form.getLocalEvento());
        ing.setDataEvento(form.getDataEvento());
        ing.setNomeComprador(form.getNomeComprador());
        ing.setCpfComprador(form.getCpfComprador());
        ing.setPrecoBase(form.getPrecoBase());
        if (form.getEventoId() != null && !form.getEventoId().isBlank()) ing.setEventoId(form.getEventoId());
        if (ing instanceof IngressoVIP vip && form.getExtra() != null) vip.setBeneficios(form.getExtra());
        if (ing instanceof IngressoMeia meia && form.getExtra() != null) meia.setDocumentoComprobatorio(form.getExtra());
        Ingresso salvo = repo.save(ing);
        salvo.setQrCodeBase64(QrCodeUtils.gerarBase64("INGRESSO:" + salvo.getId()));
        return repo.save(salvo);
    }

    private Ingresso fabricar(String tipo) {
        return switch (tipo.toUpperCase()) {
            case "VIP"  -> new IngressoVIP();
            case "MEIA" -> new IngressoMeia();
            default     -> new IngressoNormal();
        };
    }

    public List<Ingresso> listarTodos() { return repo.findAll(); }
    public List<Ingresso> listarPorUsuario(String uid) { return repo.findByUsuarioId(uid); }
    public List<Ingresso> listarPorEvento(String eid) { return repo.findByEventoId(eid); }
    public Optional<Ingresso> buscarPorId(String id) { return repo.findById(id); }
    public List<Ingresso> buscarPorEvento(String nome) { return repo.findByNomeEventoContainingIgnoreCase(nome); }
    public List<Ingresso> buscarPorStatus(String status) { return repo.findByStatus(status); }

    public Ingresso atualizarStatus(String id, String novoStatus) {
        Ingresso ing = repo.findById(id).orElseThrow(() -> new RuntimeException("Ingresso não encontrado"));
        if ("UTILIZADO".equals(ing.getStatus())) throw new RuntimeException("Ingresso já foi utilizado");
        if ("CANCELADO".equals(ing.getStatus())) throw new RuntimeException("Ingresso já está cancelado");
        if ("CANCELADO".equals(novoStatus) && ing.getEventoId() != null) eventoService.incrementar(ing.getEventoId());
        ing.setStatus(novoStatus);
        return repo.save(ing);
    }

    public Ingresso validarQr(String id) {
        Ingresso ing = repo.findById(id).orElseThrow(() -> new RuntimeException("Ingresso não encontrado"));
        if ("UTILIZADO".equals(ing.getStatus())) throw new RuntimeException("Ingresso já foi utilizado");
        if ("CANCELADO".equals(ing.getStatus())) throw new RuntimeException("Ingresso cancelado — inválido");
        ing.setStatus("UTILIZADO");
        return repo.save(ing);
    }

    public Double calcularTotalReceita() {
        return repo.findAll().stream()
                .filter(i -> !"CANCELADO".equals(i.getStatus()))
                .mapToDouble(Ingresso::calcularValor).sum();
    }

    public void deletar(String id) { repo.deleteById(id); }
}
