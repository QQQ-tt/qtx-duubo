package qtx.dubbo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author qtx
 * @since 2024/11/26 12:47
 */
@ConfigurationProperties(prefix = "reb.thread.pool")
@Component
@Data
public class ThreadPoolConfigProperties {
    private Integer coreSize;

    private Integer maxSize;

    private Integer keepAliveTime;
}
