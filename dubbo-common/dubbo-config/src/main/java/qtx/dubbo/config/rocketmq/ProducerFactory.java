package qtx.dubbo.config.rocketmq;

import org.apache.rocketmq.client.apis.*;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.apache.rocketmq.client.apis.producer.ProducerBuilder;
import org.apache.rocketmq.client.apis.producer.TransactionChecker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author qtx
 * @since 2023/7/18
 */
@Component
public class ProducerFactory {

    @Value("${rocketmq.endpoint}")
    private String endpoint;

    private static final ConcurrentMap<String, Producer> PRODUCER = new ConcurrentHashMap<>(50);
    private static final ConcurrentMap<String, Producer> TRANSACTIONAL_PRODUCER = new ConcurrentHashMap<>(100);

    private final ClientConfiguration configuration;

    public ProducerFactory(ClientConfiguration configuration) {
        this.configuration = configuration;
    }

    @Bean
    public ClientConfiguration getClientConfiguration() {
        return ClientConfiguration.newBuilder()
                .setEndpoints(endpoint)
                // On some Windows platforms, you may encounter SSL compatibility issues. Try turning off the SSL option in
                // client configuration to solve the problem please if SSL is not essential.
                // .enableSsl(false)
                .build();
    }

    private Producer buildProducer(TransactionChecker checker, String... topics) throws ClientException {
        final ClientServiceProvider provider = ClientServiceProvider.loadService();

        final ProducerBuilder builder = provider.newProducerBuilder()
                .setClientConfiguration(configuration)
                // Set the topic name(s), which is optional but recommended. It makes producer could prefetch
                // the topic route before message publishing.
                .setTopics(topics);
        if (checker != null) {
            // Set the transaction checker.
            builder.setTransactionChecker(checker);
        }
        return builder.build();
    }

    public Producer getInstance(String... topics) throws ClientException {
        Arrays.sort(topics);
        StringBuilder builder = new StringBuilder();
        for (String s : topics) {
            builder.append(s);
        }
        if (null == PRODUCER.get(builder.toString())) {
            synchronized (ProducerFactory.class) {
                if (null == PRODUCER.get(builder.toString())) {
                    PRODUCER.put(builder.toString(), buildProducer(null, topics));
                }
            }
        }
        return PRODUCER.get(builder.toString());
    }

    public Producer getTransactionalInstance(TransactionChecker checker,
                                             String... topics) throws ClientException {
        Arrays.sort(topics);
        StringBuilder builder = new StringBuilder();
        for (String s : topics) {
            builder.append(s);
        }
        if (null == TRANSACTIONAL_PRODUCER.get(builder.toString())) {
            synchronized (ProducerFactory.class) {
                if (null == TRANSACTIONAL_PRODUCER.get(builder.toString())) {
                    TRANSACTIONAL_PRODUCER.put(builder.toString(), buildProducer(checker, topics));
                }
            }
        }
        return TRANSACTIONAL_PRODUCER.get(builder.toString());
    }
}
