package qtx.dubbo.consumer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qtx.dubbo.java.Result;
import qtx.dubbo.model.entity.provider.AcBusiness;
import qtx.dubbo.service.consumer.SysUserService;

/**
 * @author qtx
 * @since 2023/7/11
 */
@Tag(name = "sysuser")
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    private final SysUserService service;

    public SysUserController(SysUserService service) {
        this.service = service;
    }

    @Operation(summary = "test_get")
    @GetMapping("/test")
    public Result<AcBusiness> test(){
        AcBusiness test = service.test();
        return Result.success(test);
    }
}
