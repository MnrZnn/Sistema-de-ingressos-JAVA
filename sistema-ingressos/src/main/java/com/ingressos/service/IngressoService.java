package com.ingressos.service;

import com.ingressos.model.*;
import com.ingressos.repository.IngressoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Camada de serviço: regras de negócio e polimorfismo em ação.
 * A factory createIngresso() retorna o tipo certo sem o controller saber qual é.
 */
@Service
public class IngressoService {

    @Autowired
    private IngressoRepository repository;

    /** Factory: cria a subclasse correta conforme o tipo informado */
    public Ingresso createIngresso(IngressoForm form) {
        Ingresso ingresso = switch (form.getTipo().toUpperCase()) {
            case "VIP"   -> new IngressoVIP();
            case "MEIA"  -> new IngressoMeia();
            default      -> new IngressoNormal();
        };

        ingresso.setNomeEvento(form.getNomeEvento());
        ingresso.setLocalEvento(form.getLocalEvento());
        ingresso.setDataEvento(form.getDataEvento());
        ingresso.setNomeComprador(form.getNomeComprador());
        ingresso.setCpfComprador(form.getCpfComprador());
        ingresso.setPrecoBase(form.getPrecoBase());

        return repository.save(ingresso);
    }

    public List<Ingresso> listarTodos() {
        return repository.findAll();
    }

    public Optional<Ingresso> buscarPorId(String id) {
        return repository.findById(id);
    }

    public List<Ingresso> buscarPorEvento(String nomeEvento) {
        return repository.findByNomeEventoContainingIgnoreCase(nomeEvento);
    }

    public List<Ingresso> buscarPorStatus(String status) {
        return repository.findByStatus(status);
    }

    public List<Ingresso> buscarPorCpf(String cpf) {
        return repository.findByCpfComprador(cpf);
    }

    /** Atualiza o status do ingresso (ATIVO -> UTILIZADO | CANCELADO) */
    public Ingresso atualizarStatus(String id, String novoStatus) {
        Ingresso ingresso = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ingresso não encontrado: " + id));
        ingresso.setStatus(novoStatus);
        return repository.save(ingresso);
    }

    public void deletar(String id) {
        repository.deleteById(id);
    }

    /** Calcula o valor total de todos os ingressos (polimorfismo) */
    public Double calcularTotalReceita() {
        return repository.findAll().stream()
            .filter(i -> !"CANCELADO".equals(i.getStatus()))
            .mapToDouble(Ingresso::calcularValor)
            .sum();
    }
}
