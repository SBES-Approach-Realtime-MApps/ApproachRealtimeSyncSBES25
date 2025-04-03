package br.unesp.rc.MSReplicator.domain.model;

import java.io.Serializable;
import java.util.List;

/**
 * Usado na entidade que ira desserializar dados de um topico Kafka
 */
public interface EntityKafka extends Serializable {

    /**
     * ID da entidade
     *
     * @return Long
     */
    Long getId();

    /**
     * Retorna as chaves para acessar os topicos definidos em application.properties
     *
     * @return List<String>
     */
    List<String> getAllKeysToAccessTopics();
}
