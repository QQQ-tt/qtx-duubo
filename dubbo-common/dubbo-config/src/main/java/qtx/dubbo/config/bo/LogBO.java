package qtx.dubbo.config.bo;

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

    private String createBy;

    private String method;

    private String path;

    private String json;

    private String param;

    public static ThreadLocal<LogBO> logBOThreadLocal = new ThreadLocal<>();
}
