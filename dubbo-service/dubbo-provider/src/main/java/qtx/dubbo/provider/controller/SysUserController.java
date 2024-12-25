package qtx.dubbo.provider.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qtx.dubbo.java.Result;
import qtx.dubbo.java.info.StaticConstant;
import qtx.dubbo.model.dto.provider.SysUserDTO;
import qtx.dubbo.model.entity.provider.SysUser;
import qtx.dubbo.service.provider.SysUserService;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author qtx
 * @since 2024-12-25
 */
@Tag(name = "用户管理")
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    private final SysUserService sysUserService;

    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Operation(summary = "分页查询")
    @PostMapping("/pageSysUser")
    public Result<Page<SysUser>> getSysUser(@RequestBody SysUserDTO dto) {
        return Result.success(sysUserService.pageSysUser(dto));
    }

    @Operation(summary = "保存或更新")
    @PostMapping("/saveOrUpdate")
    public Result<Boolean> saveOrUpdate(@RequestBody SysUser dto) {
        return Result.success(sysUserService.saveOrUpdateSysUser(dto));
    }

    @Operation(summary = "创建用户", description = StaticConstant.AUTH_KEY)
    @PostMapping("/createSysUser")
    public Result<Long> createSysUser(@RequestBody SysUser dto) {
        return Result.success(sysUserService.createSysUser(dto));
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/removeSysUser")
    public Result<Boolean> removeSysUser(@RequestParam Integer id) {
        return Result.success(sysUserService.removeSysUser(id));
    }
}
