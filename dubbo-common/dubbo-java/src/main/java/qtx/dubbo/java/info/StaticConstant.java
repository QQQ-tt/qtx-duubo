package qtx.dubbo.java.info;

/**
 * @author qtx
 * @since 2023/7/14
 */
public class StaticConstant {
    /**
     * 用户唯一信息
     */
    public static final String USER = "user";

    public static final String IP = "ip";
    /**
     * 服务认证信息
     */
    public static final String TOKEN = "Authorization";
    /** 用户登录token */
    public static final String LOGIN_USER = "login:user:";
    public static final String SYS_URL = "sys:url:";
    public static final String SYS_USER = "sys:user:";
    public static final String REDIS_INFO = ":info";

    public static final int STRING_MAX_SIZE = 255;

    public static final String STRING_SIZ_ERROR = "字符长度异常";

    public static final String ACTIVITY_PARENT = "000000";

    public static final String ROCKETMQ_KEY = "Order";
}
