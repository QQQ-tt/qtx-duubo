package qtx.dubbo.provider.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qtx.dubbo.java.Result;
import qtx.dubbo.model.entity.provider.AcBusiness;
import qtx.dubbo.service.provider.AcBusinessService;

/**
 * @author qtx
 * @since 2023/7/13
 */
@Tag(name = "测试controller")
@RestController
@RequestMapping("/acBusiness")
public class AcBusinessController {

    private final AcBusinessService service;

    public AcBusinessController(AcBusinessService service) {
        this.service = service;
    }

    @Operation(summary = "get测试接口")
    @GetMapping("/test")
    public Result<AcBusiness> test(@RequestParam boolean flag){
        return Result.success(service.test1(flag));
    }
}
