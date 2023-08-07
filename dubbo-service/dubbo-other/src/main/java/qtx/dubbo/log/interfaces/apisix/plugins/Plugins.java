package qtx.dubbo.log.interfaces.apisix.plugins;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import qtx.dubbo.log.interfaces.apisix.plugins.impl.ProxyRewrite;

/**
 * @author qtx
 * @since 2023/8/2
 */
@Data
@Builder
public class Plugins {

    @JsonProperty("proxy-rewrite")
    private ProxyRewrite proxyRewrite;
}
