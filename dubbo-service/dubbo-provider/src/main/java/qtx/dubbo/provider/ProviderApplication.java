package qtx.dubbo.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author qtx
 * @since 2023/3/30 20:32
 */
@MapperScan(basePackages = "qtx.dubbo.consumer.mapper")
@EnableDubbo(scanBasePackages = "qtx.dubbo.provider.impl")
@SpringBootApplication(scanBasePackages = "qtx.dubbo")
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
