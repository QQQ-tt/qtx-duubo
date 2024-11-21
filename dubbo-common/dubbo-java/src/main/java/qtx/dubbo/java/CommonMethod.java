package qtx.dubbo.java;

import com.alibaba.fastjson.JSONArray;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import qtx.dubbo.java.enums.DataEnums;
import qtx.dubbo.java.enums.UserInfo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qtx
 * @since 2022/11/2
 */
@Component
public class CommonMethod {
    /**
     * 用户信息
     */
    private static final ThreadLocal<Map<UserInfo, String>> mapThreadLocal = new ThreadLocal<>();
    /**
     * 公开匿名路径
     */
    private static final ThreadLocal<List<String>> authPublicPath = new ThreadLocal<>();

    /**
     * 过滤器返回信息
     *
     * @param response  response
     * @param dataEnums 错误信息
     * @throws IOException io失败
     */
    @SneakyThrows
    public static void failed(HttpServletRequest request, HttpServletResponse response, DataEnums dataEnums) throws IOException {
        request.setAttribute("filterException", dataEnums);
        request.getRequestDispatcher("/exception/filterException")
                .forward(request, response);
    }

    /**
     * 过滤器返回信息
     *
     * @param response  response
     * @param dataEnums 错误信息
     * @throws IOException io失败
     */
    public static void failed(HttpServletResponse response, String dataEnums) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        //设置响应状态码
        response.setStatus(HttpServletResponse.SC_OK);
        //输入响应内容
        PrintWriter writer = response.getWriter();
        String s = JSONArray.toJSON(Result.failed(dataEnums))
                .toString();
        writer.write(s);
        writer.flush();
    }

    public static void setMap(HttpServletRequest request, String user, String token) {
        initialize();
        Map<UserInfo, String> map = mapThreadLocal.get();
        map.put(UserInfo.USER, user);
        map.put(UserInfo.TOKEN, token);
        map.put(UserInfo.URI, request.getRequestURI());
        map.put(UserInfo.IP, request.getRemoteAddr());
    }

    public static String getUserCode() {
        initialize();
        return mapThreadLocal.get()
                .get(UserInfo.USER);
    }

    public static void setUserCode(String userCode) {
        initialize();
        mapThreadLocal.get()
                .put(UserInfo.USER, userCode);
    }

    public static String getIp() {
        return mapThreadLocal.get()
                .get(UserInfo.IP);
    }

    public static void setIp(String ip) {
        initialize();
        mapThreadLocal.get()
                .put(UserInfo.IP, ip);
    }

    public static String getUri() {
        return mapThreadLocal.get()
                .get(UserInfo.IP);
    }

    public static void setUri(String uri) {
        initialize();
        mapThreadLocal.get()
                .put(UserInfo.URI, uri);
    }

    public static String getToken() {
        return mapThreadLocal.get()
                .get(UserInfo.TOKEN);
    }

    public static void setToken(String token) {
        initialize();
        mapThreadLocal.get()
                .put(UserInfo.TOKEN, token);
    }

    public static void initialize() {
        Map<UserInfo, String> map = mapThreadLocal.get();
        if (map == null) {
            HashMap<UserInfo, String> value = new HashMap<>();
            value.put(UserInfo.USER, "1");
            mapThreadLocal.set(value);
        }
    }

    public static void remove() {
        mapThreadLocal.remove();
    }

    public static void setAuthPublicPath(List<String> authPublicPath) {
        CommonMethod.authPublicPath.set(authPublicPath);
    }

    public static String[] getAuthPublicPath() {
        List<String> strings = authPublicPath.get();
        authPublicPath.remove();
        return strings.toArray(String[]::new);
    }
}
