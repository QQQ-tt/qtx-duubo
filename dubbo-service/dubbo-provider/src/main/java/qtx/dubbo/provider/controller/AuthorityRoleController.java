package qtx.dubbo.provider.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qtx
 * @since 2024/11/20 22:26
 */
@Tag(name = "角色权限测试")
@RestController
@RequestMapping("/role")
public class AuthorityRoleController {

    @Operation(summary = "新增")
    @PostMapping("/add")
    public void add() {
        System.out.println("add");
    }

    @Operation(summary = "删除")
    @DeleteMapping("/delete")
    public void delete() {
        System.out.println("delete");
    }
}
