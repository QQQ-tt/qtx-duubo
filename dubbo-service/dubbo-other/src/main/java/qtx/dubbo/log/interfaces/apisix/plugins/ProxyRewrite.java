package qtx.dubbo.log.interfaces.apisix.plugins;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

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
