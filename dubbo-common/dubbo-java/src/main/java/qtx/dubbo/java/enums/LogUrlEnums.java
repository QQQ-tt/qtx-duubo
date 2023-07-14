package qtx.dubbo.java.enums;

import org.springframework.util.AntPathMatcher;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author qtx
 * @since 2023/7/14
 */
public enum LogUrlEnums {

    /**
     * 放行地址:验证码
     */
    SWAGGER_URL("/v3/api-docs"),
    /**
     * 方形地址:文件
     */
    FILE_URL("/file/*"),
    /**
     * 放行地址:dubbo
     */
    DUBBO("/");


    private final String context;

    LogUrlEnums(String context) {
        this.context = context;
    }


    public static boolean logPath(String path) {
        AntPathMatcher matcher = new AntPathMatcher();
        for (String s : LOG) {
            if (matcher.match(s, path)){
                return true;
            }
        }
        return false;
    }

    private final static Set<String> LOG = new HashSet<>();

    static {
        Stream.of(LogUrlEnums.values())
                .forEach(e -> LogUrlEnums.LOG.add(e.context));
    }
}
