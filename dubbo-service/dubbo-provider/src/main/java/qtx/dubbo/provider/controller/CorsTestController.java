package qtx.dubbo.provider.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qtx.dubbo.java.Result;
import qtx.dubbo.java.info.StaticConstant;
import qtx.dubbo.redis.util.RedisUtils;
import qtx.dubbo.security.Login;
import qtx.dubbo.security.util.JwtUtils;

import java.util.HashMap;

/**
 * @author qtx
 * @since 2023/7/21
 */
@Tag(name = "cors测试")
@RestController
@RequestMapping("/login")
public class CorsTestController {

    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("/selectAll")
    public Result<Object> test() {
        return Result.success();
    }

    @PostMapping("/login")
    public Result<Login> test1(@RequestBody Login login) {
        String userCode = login.getUserCode();
        HashMap<String, Object> map = new HashMap<>();
        map.put("roles", "abc");
        login.setToken(JwtUtils.generateToken(userCode, map));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userCode", userCode);
        redisUtils.addHashMsgAll(
                StaticConstant.LOGIN_USER + userCode + StaticConstant.REDIS_INFO, hashMap);
        return Result.success(login);
    }
}
