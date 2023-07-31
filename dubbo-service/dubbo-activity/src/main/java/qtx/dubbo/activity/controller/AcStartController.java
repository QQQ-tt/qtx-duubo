package qtx.dubbo.activity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import qtx.dubbo.java.Result;
import qtx.dubbo.model.dto.activity.AcStartUpdateDTO;
import qtx.dubbo.model.vo.activity.AcToDoVO;
import qtx.dubbo.model.vo.activity.TaskVO;
import qtx.dubbo.service.activity.AcStartService;

import java.util.List;

/**
 * <p>
 * 流程启动表 前端控制器
 * </p>
 *
 * @author qtx
 * @since 2023-07-26
 */
@RestController
@RequestMapping("/acStart")
public class AcStartController {

    private final AcStartService acStartService;

    public AcStartController(AcStartService acStartService) {
        this.acStartService = acStartService;
    }

    @Operation(summary = "开启流程")
    @Parameter(name = "acUuid", description = "流程uuid")
    @GetMapping("/acStart")
    public Result<String> acStart(@RequestParam String acUuid) {
        return Result.success(acStartService.startAc(acUuid));
    }

    @Operation(summary = "查询流程")
    @Parameter(name = "taskUuid", description = "任务uuid")
    @GetMapping("/listTask")
    public Result<List<TaskVO>> listTask(@RequestParam String taskUuid) {
        return Result.success(acStartService.listTask(taskUuid));
    }

    @Operation(summary = "更新节点")
    @PostMapping("/updateAc")
    public Result<Boolean> updateAc(@RequestBody @Valid AcStartUpdateDTO dto) {
        return Result.success(acStartService.updateAc(dto));
    }

    @Operation(summary = "待办")
    @Parameters(
            value = {
                    @Parameter(name = "acUuid", description = "流程uuid"),
                    @Parameter(name = "userCard", description = "用户账户")
            })
    @GetMapping("/toDo")
    public Result<List<AcToDoVO>> toDo(String acUuid, String userCard) {
        return Result.success(acStartService.toDo(acUuid, userCard));
    }
}
