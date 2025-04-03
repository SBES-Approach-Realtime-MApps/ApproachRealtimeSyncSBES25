package br.unesp.rc.MSReplicator.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Enum usado para obter a acao realizada no debezium
 * <a href="https://debezium.io/documentation/reference/stable/connectors/postgresql.html#postgresql-create-events">...</a>
 */
@Getter
@RequiredArgsConstructor
public enum DebeziumOperation {

    READ("r"),
    CREATE("c"),
    UPDATE("u"),
    DELETE("d"),
    TRUNCATE("t"),
    UNKNOWN("other");

    private final String value;

    public static DebeziumOperation of(String operation) {
        return Arrays.stream(DebeziumOperation.values())
                .filter(op -> op.getValue().equals(operation)).findFirst()
                .orElse(UNKNOWN);
    }
}
