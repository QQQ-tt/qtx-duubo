package qtx.dubbo.log.interfaces.apisix.plugins;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author qtx
 * @since 2023/8/2
 */
@Data
public class Plugins {

    @JsonProperty("proxy-rewrite")
    private Plugin proxyRewrite;
}
