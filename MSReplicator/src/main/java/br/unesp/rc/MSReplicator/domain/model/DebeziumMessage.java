package br.unesp.rc.MSReplicator.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.kafka.support.Acknowledgment;

/**
 * Classe com os dados recebidos de um topico usado pelo debezium
 * <a href="https://debezium.io/documentation/reference/stable/connectors/postgresql.html#postgresql-create-events">...</a>
 *
 * @author Ernani Lima
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public final class DebeziumMessage<P> {

    /**
     * Todos os dados relacionados a construcao da entidade recebida
     */
    private final Object schema;

    /**
     * Dados com a entidade e a acao realizada
     */
    private final DebeziumPayload<P> payload;

    /**
     * Dados enviados pelo kafka, usado para que seja realizado
     * o commit quando a mensagem for processada com sucesso
     */
    private Acknowledgment acknowledgment;

    /**
     * Construtor usado pelo deserializer
     *
     * @param schema  Object
     * @param payload DebeziumPayload<P>
     */
    @JsonCreator
    public DebeziumMessage(@JsonProperty("schema") Object schema,
                           @JsonProperty("payload") DebeziumPayload<P> payload) {
        this.schema = schema;
        this.payload = payload;
    }

    /**
     * Construtor usado para definir o 'acknowledgment' da mensagem recebida
     *
     * @param schema         Object
     * @param payload        DebeziumPayload<P>
     * @param acknowledgment Acknowledgment
     */
    public DebeziumMessage(Object schema, DebeziumPayload<P> payload, Acknowledgment acknowledgment) {
        this.schema = schema;
        this.payload = payload;
        this.acknowledgment = acknowledgment;
    }
}
