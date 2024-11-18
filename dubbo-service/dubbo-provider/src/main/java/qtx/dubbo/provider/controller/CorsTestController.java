package qtx.dubbo.provider.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qtx.dubbo.java.Result;
import qtx.dubbo.java.info.StaticConstant;
import qtx.dubbo.java.util.JwtUtils;
import qtx.dubbo.redis.util.RedisUtils;
import qtx.dubbo.security.Login;


import java.util.HashMap;
import java.util.Map;

/**
 * @author qtx
 * @since 2023/7/21
 */
@Tag(name = "cors测试")
@RestController
@RequestMapping("/login")
public class CorsTestController {

    private final RedisUtils redisUtils;

    private final PasswordEncoder passwordEncoder;

    public CorsTestController(RedisUtils redisUtils, PasswordEncoder passwordEncoder) {
        this.redisUtils = redisUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/selectAll")
    public Result<Object> test() {
        return Result.success();
    }

    @PostMapping("/login")
    public Result<Login> test1(@RequestBody Login login) {
        String userCode = login.getUserCode();
        String password = login.getPassword();
        HashMap<String, Object> map = new HashMap<>();
        map.put("roles", "abc");
        login.setToken(JwtUtils.generateToken(userCode, map));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userCode", userCode);
        hashMap.put("password", password);
        redisUtils.addHashMsgAll(
                StaticConstant.LOGIN_USER + userCode + StaticConstant.REDIS_INFO, hashMap);
        return Result.success(login);
    }

    @PostConstruct
    public void initUser() {
        String userCode = "11022";
        Map<String, String> map = Map.of("userCode", userCode, "password", passwordEncoder.encode("123456"));
           redisUtils.addHashMsgAll(
                StaticConstant.SYS_USER + userCode + StaticConstant.REDIS_INFO, map);
    }
}
