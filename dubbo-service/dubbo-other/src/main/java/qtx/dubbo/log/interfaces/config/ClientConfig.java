package qtx.dubbo.log.interfaces.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import qtx.dubbo.log.interfaces.ApiSixClient;

/**
 * @author qtx
 * @since 2023/8/2
 */
@Configuration
public class ClientConfig {

    @Value("${apisix.url}")
    private String apisixServiceUrl;

    @Bean
    public WebClient getWebClient() {
        return WebClient.builder()
                .baseUrl(apisixServiceUrl)
                .build();
    }

    @Bean
    public ApiSixClient requestService(WebClient webClient) {
        HttpServiceProxyFactory proxyFactory =
                HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient))
                        .build();
        return proxyFactory.createClient(ApiSixClient.class);
    }

}
