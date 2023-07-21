package qtx.dubbo.provider.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qtx.dubbo.java.Result;

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

    @GetMapping("/login")
    public Result<Object> test1(){
        return Result.success();
    }
}
