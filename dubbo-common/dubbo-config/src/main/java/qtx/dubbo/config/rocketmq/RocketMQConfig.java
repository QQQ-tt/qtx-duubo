package qtx.dubbo.config.rocketmq;

import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qtx
 * @since 2023/7/19
 */
@Configuration
@ConditionalOnProperty(name = "rocketmq.enable",havingValue = "true")
public class RocketMQConfig {


    @Value("${rocketmq.endpoint}")
    private String endpoint;

    @Bean
    public ClientConfiguration getClientConfiguration() {
        return ClientConfiguration.newBuilder()
                .setEndpoints(endpoint)
                // On some Windows platforms, you may encounter SSL compatibility issues. Try turning off the SSL option in
                // client configuration to solve the problem please if SSL is not essential.
                // .enableSsl(false)
                .build();
    }
}
