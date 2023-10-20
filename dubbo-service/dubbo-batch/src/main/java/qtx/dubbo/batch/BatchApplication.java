package qtx.dubbo.batch;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author qtx
 * @since 2023/9/26
 */
@ServletComponentScan(basePackages = "qtx.dubbo.config")
@EnableDubbo(scanBasePackages = "qtx.dubbo.batch.impl")
@SpringBootApplication(scanBasePackages = "qtx.dubbo")
public class BatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchApplication.class, args);
    }
}
