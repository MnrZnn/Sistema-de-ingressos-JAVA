package com.ingressos.config;

import com.ingressos.model.Ingresso;
import com.ingressos.model.IngressoNormal;
import com.ingressos.model.IngressoVIP;
import com.ingressos.model.IngressoMeia;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * Configura o discriminador de tipo para salvar subclasses no MongoDB.
 * O campo "_class" identifica qual subclasse instanciar ao recuperar.
 */
@Configuration
public class MongoConfig {
    // Spring Data MongoDB gerencia o mapeamento de herança via _class automaticamente.
    // Nenhuma config adicional é necessária para este projeto.
}
