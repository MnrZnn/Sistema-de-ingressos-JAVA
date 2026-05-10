package com.ingressos.service;

import com.ingressos.model.Evento;
import com.ingressos.repository.EventoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    private final EventoRepository repo;

    public EventoService(EventoRepository repo) { this.repo = repo; }

    public Evento salvar(Evento e) { return repo.save(e); }
    public List<Evento> listarAtivos() { return repo.findByAtivoTrue(); }
    public List<Evento> listarTodos() { return repo.findAll(); }
    public Optional<Evento> buscarPorId(String id) { return repo.findById(id); }
    public void deletar(String id) { repo.deleteById(id); }

    public void decrementar(String eventoId) {
        repo.findById(eventoId).ifPresent(e -> {
            if (e.getIngressosDisponiveis() > 0) {
                e.setIngressosDisponiveis(e.getIngressosDisponiveis() - 1);
                repo.save(e);
            }
        });
    }

    public void incrementar(String eventoId) {
        repo.findById(eventoId).ifPresent(e -> {
            e.setIngressosDisponiveis(e.getIngressosDisponiveis() + 1);
            repo.save(e);
        });
    }
}
