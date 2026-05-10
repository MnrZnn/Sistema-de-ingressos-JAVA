package com.ingressos.repository;

import com.ingressos.model.Ingresso;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface IngressoRepository extends MongoRepository<Ingresso, String> {
    List<Ingresso> findByNomeEventoContainingIgnoreCase(String nome);
    List<Ingresso> findByStatus(String status);
    List<Ingresso> findByCpfComprador(String cpf);
    List<Ingresso> findByUsuarioId(String usuarioId);
    List<Ingresso> findByEventoId(String eventoId);
}
