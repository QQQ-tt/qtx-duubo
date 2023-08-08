package qtx.dubbo.config.init.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qtx
 * @since 2023/8/4
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UrlBO {

    private String serviceName;

    /**
     * 请求名称
     */
    private String name;

    /**
     * 请求地址
     */
    private String uri;

    /**
     * bean名字
     */
    private String bean;

    /**
     * 请求类型
     */
    private String requestType;

    /**
     * 上游id
     */
    private String upstreamId;
}
