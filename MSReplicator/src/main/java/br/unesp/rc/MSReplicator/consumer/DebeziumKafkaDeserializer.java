package br.unesp.rc.MSReplicator.consumer;

import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.unesp.rc.MSReplicator.domain.model.DebeziumMessage;
import br.unesp.rc.MSReplicator.domain.model.EntityKafka;
import br.unesp.rc.MSReplicator.domain.strategy.KafkaEntityStrategy;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Component
@RequiredArgsConstructor
public class DebeziumKafkaDeserializer<T> implements Deserializer<T> {
    private final KafkaEntityStrategy kafkaEntityStrategy;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public T deserialize(String topic, byte[] data) {
        EntityKafka entityKafka = kafkaEntityStrategy.findClasseByTopic(topic);

        JavaType debeziumMessageWithEntityKafka = objectMapper.getTypeFactory()
                .constructParametricType(DebeziumMessage.class, entityKafka.getClass());

        return objectMapper.readValue(data, debeziumMessageWithEntityKafka);
    }
}