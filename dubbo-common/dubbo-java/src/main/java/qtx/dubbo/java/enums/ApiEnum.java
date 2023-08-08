package qtx.dubbo.java.enums;

import org.springframework.util.AntPathMatcher;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author qtx
 * @since 2023/8/8
 */
public enum ApiEnum {

    API_SWAGGER_1("/v3/api-docs*"),
    API_SWAGGER_2("/v3/api-docs/*"),
    API_SWAGGER_3("/v3/api-docs.yaml/*"),
    API_SWAGGER_4("/swagger-ui.html");


    private final String context;

    ApiEnum(String context) {
        this.context = context;
    }

    public static boolean authPath(String path) {
        AntPathMatcher matcher = new AntPathMatcher();
        for (String s : API) {
            if (matcher.match(s, path)) {
                return true;
            }
        }
        return false;
    }

    private final static Set<String> API = new HashSet<>();

    static {
        Stream.of(ApiEnum.values())
                .forEach(e -> ApiEnum.API.add(e.context));
    }
}
