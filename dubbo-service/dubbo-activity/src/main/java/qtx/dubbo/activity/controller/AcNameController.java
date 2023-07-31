package qtx.dubbo.activity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qtx.dubbo.java.Result;
import qtx.dubbo.model.dto.activity.ActivityDTO;
import qtx.dubbo.service.activity.AcNameService;

/**
 * <p>
 * 单一流程名称表 前端控制器
 * </p>
 *
 * @author qtx
 * @since 2023-07-26
 */
@Tag(name = "流程管理")
@RestController
@RequestMapping("/acName")
public class AcNameController {
    private final AcNameService acNameService;

    public AcNameController(AcNameService acNameService) {
        this.acNameService = acNameService;
    }

    @Operation(summary = "创建或更新流程")
    @PostMapping("/saveOrUpdateCa")
    public Result<String> saveOrUpdateCa(@RequestBody ActivityDTO dto) {
        return Result.success(acNameService.saveOrUpdateAc(dto));
    }
}
