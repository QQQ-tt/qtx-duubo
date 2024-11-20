package qtx.dubbo.provider.controller;

import io.swagger.v3.oas.annotations.Operation;
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
import qtx.dubbo.model.entity.provider.AcBusiness;
import qtx.dubbo.provider.impl.AcBusinessServiceTwoImpl;
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

    private final AcBusinessServiceTwoImpl acBusinessServiceTwo;

    public CorsTestController(RedisUtils redisUtils, PasswordEncoder passwordEncoder, AcBusinessServiceTwoImpl acBusinessServiceTwo) {
        this.redisUtils = redisUtils;
        this.passwordEncoder = passwordEncoder;
        this.acBusinessServiceTwo = acBusinessServiceTwo;
    }

    @Operation(summary = "测试接口")
    @GetMapping("/selectAll")
    public Result<AcBusiness> test() {
        return Result.success(acBusinessServiceTwo.test1(false));
    }

    @PostMapping("/login")
    public Result<Login> test1(@RequestBody Login login) {
        String userCode = login.getUserCode();
        String password = login.getPassword();
        HashMap<String, Object> map = new HashMap<>();
        String s = JwtUtils.generateToken(userCode, map);
        map.put("roles", "abc");
        login.setToken(s);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userCode", userCode);
        hashMap.put("password", password);
        hashMap.put("token", s);
        hashMap.put("roles", "abc");
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
