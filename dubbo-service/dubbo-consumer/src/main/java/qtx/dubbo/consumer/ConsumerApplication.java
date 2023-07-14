package qtx.dubbo.consumer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author qtx
 * @since 2023/3/30 20:32
 */
@ServletComponentScan(basePackages = "qtx.dubbo.config")
@MapperScan(basePackages = "qtx.dubbo.consumer.mapper")
@EnableDubbo(scanBasePackages = "qtx.dubbo.consumer.impl")
@SpringBootApplication(scanBasePackages = "qtx.dubbo")
public class ConsumerApplication {
    public static void main(String[] args) {
    SpringApplication.run(ConsumerApplication.class);
    }
}
