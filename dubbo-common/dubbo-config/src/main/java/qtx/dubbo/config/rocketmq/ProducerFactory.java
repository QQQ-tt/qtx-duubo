package qtx.dubbo.config.rocketmq;

import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.apache.rocketmq.client.apis.producer.ProducerBuilder;
import org.apache.rocketmq.client.apis.producer.TransactionChecker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author qtx
 * @since 2023/7/18
 */
@Component
@ConditionalOnProperty(name = "rocketmq.enable",havingValue = "true")
public class ProducerFactory {

    private static final ConcurrentMap<String, Producer> PRODUCER = new ConcurrentHashMap<>(50);
    private static final ConcurrentMap<String, Producer> TRANSACTIONAL_PRODUCER = new ConcurrentHashMap<>(100);

    private final ClientConfiguration configuration;

    public ProducerFactory(ClientConfiguration configuration) {
        this.configuration = configuration;
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
