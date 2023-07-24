package qtx.dubbo.activity;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author qtx
 * @since 2023/7/21
 */
@ServletComponentScan(basePackages = "qtx.dubbo.config")
@MapperScan(basePackages = "qtx.dubbo.activity.mapper")
@EnableDubbo(scanBasePackages = "qtx.dubbo.activity.impl")
@SpringBootApplication(scanBasePackages = "qtx.dubbo")
public class ActivityApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivityApplication.class, args);
    }
}
