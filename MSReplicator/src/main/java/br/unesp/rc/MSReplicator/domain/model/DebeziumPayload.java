package br.unesp.rc.MSReplicator.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * Classe com os dados da entidade e a acao realizada
 * <a href="https://debezium.io/documentation/reference/stable/connectors/postgresql.html#postgresql-create-events">...</a>
 *
 * @author Ernani Lima
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public final class DebeziumPayload<P> {

    /**
     * Dados da entidade, apenas para acao de 'delete'
     */
    private final P before;

    /**
     * Dados da entidade, apenas para acao de 'create' ou 'update'
     */
    private final P after;

    /**
     * Acao realizada, pode ser 'create', 'update', 'delete' ou 'truncate'
     */
    private final DebeziumOperation operation;

    /**
     * Construtor usado pelo deserializer
     *
     * @param before P
     * @param after  P
     * @param op     String
     */
    @JsonCreator
    public DebeziumPayload(@JsonProperty("before") P before,
                           @JsonProperty("after") P after,
                           @JsonProperty("op") String op) {
        this.before = before;
        this.after = after;
        this.operation = DebeziumOperation.of(op);
    }
}
