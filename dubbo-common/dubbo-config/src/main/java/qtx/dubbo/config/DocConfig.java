package qtx.dubbo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author qtx
 * @since 2023/6/26
 */
@Configuration
// Enable dubbo api doc
//@EnableDubboApiDocs
@Profile("dev")
public class DocConfig {

}
