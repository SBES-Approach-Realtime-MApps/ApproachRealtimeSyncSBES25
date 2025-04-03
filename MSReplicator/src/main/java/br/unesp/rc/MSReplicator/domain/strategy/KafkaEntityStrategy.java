package br.unesp.rc.MSReplicator.domain.strategy;

import br.unesp.rc.MSReplicator.MsReplicatorApplication;
import br.unesp.rc.MSReplicator.domain.model.EntityKafka;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class KafkaEntityStrategy {
    private final Environment env;
    private final Map<String, EntityKafka> topicsWithEntities = new HashMap<>();

    @PostConstruct
    public void init() {
        findAllClassesWithImplementation(EntityKafka.class);
    }

    /**
     * Busca uma entidade pelo topico que ela atende
     *
     * @param topic String
     * @return EntityKafka
     */
    public EntityKafka findClasseByTopic(String topic) {
        return topicsWithEntities.get(topic);
    }

    /**
     * Busca todas as entidades que implementam a classe passada por parametro,
     * cria uma lista com as entidades e o topico que ela atende
     *
     * @param interfaceClass Class<?>
     */
    @SneakyThrows
    protected void findAllClassesWithImplementation(Class<?> interfaceClass) {
        var classPathScanningCandidateComponentProvider = new ClassPathScanningCandidateComponentProvider(false);
        classPathScanningCandidateComponentProvider.addIncludeFilter(new AssignableTypeFilter(interfaceClass));

        for (BeanDefinition bd : classPathScanningCandidateComponentProvider
                .findCandidateComponents(MsReplicatorApplication.class.getPackageName())) {
            Class<?> clazz = Class.forName(bd.getBeanClassName());
            EntityKafka entityKafka = (EntityKafka) clazz.getDeclaredConstructor().newInstance();

            entityKafka.getAllKeysToAccessTopics().forEach(topic ->
                    topicsWithEntities.put(env.getProperty(topic), entityKafka));
        }
    }
}
