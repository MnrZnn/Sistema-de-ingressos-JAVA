package com.ingressos.repository;

import com.ingressos.model.Evento;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface EventoRepository extends MongoRepository<Evento, String> {
    List<Evento> findByAtivoTrue();
}
