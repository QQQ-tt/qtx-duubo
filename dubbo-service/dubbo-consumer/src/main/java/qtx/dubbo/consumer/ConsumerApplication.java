package qtx.dubbo.consumer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author qtx
 * @since 2023/4/3 16:20
 */
@EnableDubbo(scanBasePackages = "qtx.dubbo.consumer.impl")
@SpringBootApplication
public class ConsumerApplication {
  public static void main(String[] args) {
    ConfigurableApplicationContext applicationContext = SpringApplication.run(ConsumerApplication.class, args);
    String userName = applicationContext.getEnvironment().getProperty("user.name");
    String userAge = applicationContext.getEnvironment().getProperty("user.age");
    System.err.println("user name :"+userName+"; age: "+userAge);
  }
}
