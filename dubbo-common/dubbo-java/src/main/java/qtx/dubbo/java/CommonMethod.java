package qtx.dubbo.java;

import com.alibaba.fastjson.JSONArray;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import qtx.dubbo.java.enums.DataEnums;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author qtx
 * @since 2022/11/2
 */
@Component
public class CommonMethod {

    /**
     * 获取当前登录人userCode
     */
    private final ThreadLocal<String> userCode = new ThreadLocal<>();

    /**
     * 获取请求ip
     */
    private final ThreadLocal<String> ip = new ThreadLocal<>();

    /**
     * 请求地址
     */
    private final ThreadLocal<String> uri = new ThreadLocal<>();

    private final ThreadLocal<String> token = new ThreadLocal<>();


    /**
     * 过滤器返回信息
     *
     * @param response  response
     * @param dataEnums 错误信息
     * @throws IOException io失败
     */
    public static void failed(HttpServletResponse response, DataEnums dataEnums) throws IOException {
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

    public String getUserCode() {
        return userCode.get();
    }

    public void setUserCode(String userCode) {
        this.userCode.set(userCode);
    }

    public String getIp() {
        return ip.get();
    }

    public void setIp(String ip) {
        this.ip.set(ip);
    }

    public String getUri() {
        return uri.get();
    }

    public void setUri(String uri) {
        this.uri.set(uri);
    }

    public String getToken() {
        return token.get();
    }

    public void setToken(String token){
         this.token.set(token);
    }
}
