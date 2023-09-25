package qtx.dubbo.java.enums;

import org.springframework.util.AntPathMatcher;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author qtx
 * @since 2023/7/14
 */
public enum AuthUrlEnums {
    /**
     * 放行地址:swagger
     */
    SWAGGER_URL("/v3/api-docs", 1),
    SWAGGER_URL1("/swagger-ui.html", 0),
    /**
     * 方形地址:文件
     */
    FILE_URL("/file/*", 1),
    /**
     * 放行地址:注册
     */
    REGISTER("/auth/sysUser/createUser", 0),
    /**
     * 放行地址:dubbo
     */
    DUBBO("/", 0),
    /**
     * 放行地址:登录
     */
    LOGIN("/auth/sysUser/login", 0),
    /**
     * 放行地址:验证码
     */
    AUTH_CODE("/auth/sysUser/getAuthCode", 0),
    /**
     * 放行地址:刷新token
     */
    REFRESH_TOKEN("/auth/sysUser/token", 0),
    /**
     * 测试接口放行
     */
    TEST("/sysUser/test", 0),
    TEST1("/login/selectAll", 0);


    private final String context;

    /**
     * 路径匹配类型；0 完全匹配，1 动态匹配
     */
    private final int pathType;

    AuthUrlEnums(String context, int pathType) {
        this.context = context;
        this.pathType = pathType;
    }


    public static boolean authPath(String path) {
        if (AUTH_1.contains(path)) {
            return true;
        }
        AntPathMatcher matcher = new AntPathMatcher();
        for (String s : AUTH_2) {
            if (matcher.match(s, path)) {
                return true;
            }
        }
        return false;
    }

    private final static Set<String> AUTH_1 = new HashSet<>();
    private final static Set<String> AUTH_2 = new HashSet<>();

    static {
        Stream.of(AuthUrlEnums.values())
                .forEach(e -> {
                    if (e.pathType == 0) {
                        AuthUrlEnums.AUTH_1.add(e.context);
                    } else {
                        AuthUrlEnums.AUTH_2.add(e.context);
                    }
                });
    }
}
