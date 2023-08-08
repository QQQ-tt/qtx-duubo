package qtx.dubbo.log;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author qtx
 * @since 2023/3/30 20:32
 */
@EnableAsync
@MapperScan(basePackages = "qtx.dubbo.log.mapper")
@EnableDubbo(scanBasePackages = "qtx.dubbo.log.impl")
@SpringBootApplication(scanBasePackages = "qtx.dubbo")
public class OtherApplication {

    public static void main(String[] args) {
        SpringApplication.run(OtherApplication.class);
    }
}
