package qtx.dubbo.consumer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qtx.dubbo.consumer.impl.SysUserServiceImpl;
import qtx.dubbo.service.consumer.SysUserService;

/**
 * 用户表 前端控制器
 *
 * @author qtx
 * @since 2023-04-05
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

  private final SysUserServiceImpl sysUserService;

  public SysUserController(SysUserServiceImpl sysUserService) {
    this.sysUserService = sysUserService;
  }

  @GetMapping("/listAc")
  public Object listAc() {
    return sysUserService.listAc();
  }

  @GetMapping("/test")
  public String test() {
    return sysUserService.test();
  }
}
