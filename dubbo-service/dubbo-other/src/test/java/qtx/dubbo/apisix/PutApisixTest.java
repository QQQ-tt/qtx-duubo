package qtx.dubbo.apisix;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import qtx.dubbo.log.OtherApplication;
import qtx.dubbo.log.interfaces.ApiSixClient;
import qtx.dubbo.log.interfaces.apisix.ApiEntity;
import qtx.dubbo.log.interfaces.apisix.Plugins;
import qtx.dubbo.log.interfaces.apisix.plugins.ProxyRewrite;

import java.util.Arrays;

/**
 * @author qtx
 * @since 2023/8/2
 */
//@SpringBootTest(classes = OtherApplication.class)
public class PutApisixTest {

//    @Autowired
    private ApiSixClient apiSixClient;


//    @Test
    public void print() throws JsonProcessingException {
        ApiEntity entity = getApiEntity();
        System.out.println(new ObjectMapper().writeValueAsString(entity));
    }

//    @Test
    public void test() {
        String map = apiSixClient.addRoute("edd1c9f034335f136f87ad84b625c8f1",
                4,
                getApiEntity());
        System.out.println(map);

    }

    private ApiEntity getApiEntity() {
        Plugins plugins = Plugins.builder()
                .proxyRewrite(ProxyRewrite.builder()
                        .method("GET")
                        .regexUri(Arrays.asList("^/c/(.*)$", "/$1"))
                        .build())
                .build();
        return ApiEntity.builder()
                .uri("/c/sysNation/selectAll1")
                .name("哈哈哈")
                .methods(Arrays.asList("GET", "POST"))
                .upstreamId("468571133277373123")
                .plugins(plugins)
                .build();
    }
}
