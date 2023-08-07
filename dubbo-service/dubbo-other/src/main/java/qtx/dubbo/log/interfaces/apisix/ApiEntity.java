package qtx.dubbo.log.interfaces.apisix;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import qtx.dubbo.log.interfaces.apisix.plugins.Plugins;

import java.util.List;

/**
 * @author qtx
 * @since 2023/8/2
 */
@Data
@Builder
public class ApiEntity {

    /**
     * 请求名称
     */
    private String name;

    /**
     * 请求地址
     */
    private String uri;

    /**
     * 请求类型
     */
    private List<String> methods;

    /**
     * 上游id
     */
    @JsonProperty("upstream_id")
    private String upstreamId;

    /**
     * 插件
     */
    private Plugins plugins;
}
