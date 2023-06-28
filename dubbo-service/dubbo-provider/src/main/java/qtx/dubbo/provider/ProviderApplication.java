package qtx.dubbo.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author qtx
 * @since 2023/3/30 20:32
 */
@EnableDubbo(scanBasePackages = "qtx.dubbo.provider.impl")
@SpringBootApplication(scanBasePackages = "qtx.dubbo")
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
