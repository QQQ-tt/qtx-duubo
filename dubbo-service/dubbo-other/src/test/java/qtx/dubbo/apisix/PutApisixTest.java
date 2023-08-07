package qtx.dubbo.apisix;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import qtx.dubbo.log.interfaces.apisix.ApiEntity;
import qtx.dubbo.log.interfaces.apisix.plugins.Plugins;
import qtx.dubbo.log.interfaces.apisix.plugins.impl.ProxyRewrite;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author qtx
 * @since 2023/8/2
 */
public class PutApisixTest {

    public static void main(String[] args) throws JsonProcessingException {
        Plugins plugins = new Plugins();
        Plugins proxyRewrite = ProxyRewrite.Builder(plugins)
                .method(Collections.singletonList("GET"))
                .regexUri(Arrays.asList("^/c/(.*)$", "/$1"))
                .build();
        ApiEntity entity = ApiEntity.builder()
                .url("/c/sysNation/selectAll1")
                .name("哈哈哈")
                .methods(Arrays.asList("GET", "POST"))
                .upstreamId("1")
                .plugins(plugins)
                .build();
        System.out.println(new ObjectMapper().writeValueAsString(entity));
    }
}
