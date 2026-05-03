package com.ingressos.repository;

import com.ingressos.model.Ingresso;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IngressoRepository extends MongoRepository<Ingresso, String> {
    List<Ingresso> findByStatus(String status);
    List<Ingresso> findByNomeEventoContainingIgnoreCase(String nomeEvento);
    List<Ingresso> findByCpfComprador(String cpf);
}
