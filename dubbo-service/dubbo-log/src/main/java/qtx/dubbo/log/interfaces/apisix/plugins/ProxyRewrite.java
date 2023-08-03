package qtx.dubbo.log.interfaces.apisix.plugins;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author qtx
 * @since 2023/8/2
 */
@Data
public class ProxyRewrite implements Plugin {

    private List<String> method;

    @JsonProperty("regex_uri")
    private List<String> regexUri;

    private ProxyRewrite() {

    }

    public static Builder Builder(Plugins plugins) {
        return new Builder(plugins);
    }

    public static class Builder extends ProxyRewrite {

        private final Plugins plugins;

        private Builder(Plugins plugins) {
            this.plugins = plugins;
        }

        public Builder method(List<String> method) {
            super.method = method;
            return this;
        }

        public Builder regexUri(List<String> regexUri) {
            if (regexUri.size() > 2) {
                throw new RuntimeException("数据异常");
            }
            super.regexUri = regexUri;
            return this;
        }

        public Plugins build() {
            plugins.setProxyRewrite(this);
            return plugins;
        }
    }
}
