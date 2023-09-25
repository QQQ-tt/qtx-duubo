package qtx.dubbo.config.utils;

import com.alibaba.fastjson.JSONArray;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import qtx.dubbo.java.Result;
import qtx.dubbo.java.enums.DataEnums;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author qtx
 * @since 2022/11/2
 */
@Setter
@Getter
@Component
public class CommonMethod {

    /**
     * 获取当前登录人userCode
     */
    private String userCode;

    /**
     *  获取请求ip
     */
    private String ip;

    /**
     * 请求地址
     */
    private String uri;


    /**
     * 过滤器返回信息
     *
     * @param response  response
     * @param dataEnums 错误信息
     * @throws IOException io失败
     */
    public void failed(HttpServletResponse response, DataEnums dataEnums) throws IOException {
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

}
