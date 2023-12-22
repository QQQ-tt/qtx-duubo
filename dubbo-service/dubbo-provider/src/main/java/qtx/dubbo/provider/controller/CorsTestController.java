package qtx.dubbo.provider.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import qtx.dubbo.java.Result;
import qtx.dubbo.security.Login;

/**
 * @author qtx
 * @since 2023/7/21
 */
@Tag(name = "cors测试")
@RestController
@RequestMapping("/login")
public class CorsTestController {

    @GetMapping("/selectAll")
    public Result<Object> test(){
        return Result.success();
    }

    @PostMapping("/login")
    public Result<Login> test1(@RequestBody Login login){
        return Result.success(login);
    }
}
