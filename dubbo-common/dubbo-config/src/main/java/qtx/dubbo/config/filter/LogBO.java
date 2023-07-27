package qtx.dubbo.config.filter;

import lombok.Builder;
import lombok.Data;

/**
 * @author qtx
 * @since 2023/7/27
 */
@Data
@Builder
public class LogBO {

    private String userCode;

    private String method;

    private String path;

    private String json;

    private String param;
}
