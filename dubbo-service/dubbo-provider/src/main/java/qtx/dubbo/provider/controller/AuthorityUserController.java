package qtx.dubbo.provider.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qtx.dubbo.java.Result;

/**
 * @author qtx
 * @since 2024/11/20 22:26
 */
@Tag(name = "用户权限测试")
@RestController
@RequestMapping("/user")
public class AuthorityUserController {

    @Operation(summary = "新增")
    @PostMapping("/add")
    public Result<Object> add() {
        System.out.println("add");
        return Result.success();
    }

    @Operation(summary = "删除")
    @DeleteMapping("/delete")
    public void delete() {
        System.out.println("delete");
    }
}
