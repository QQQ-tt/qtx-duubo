package qtx.dubbo.log.interfaces.apisix.plugins.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import qtx.dubbo.log.interfaces.apisix.plugins.Plugin;
import qtx.dubbo.log.interfaces.apisix.plugins.Plugins;

import java.util.List;

/**
 * @author qtx
 * @since 2023/8/2
 */
@Data
@Builder
public class ProxyRewrite {

    private String method;

    @JsonProperty("regex_uri")
    private List<String> regexUri;
}
