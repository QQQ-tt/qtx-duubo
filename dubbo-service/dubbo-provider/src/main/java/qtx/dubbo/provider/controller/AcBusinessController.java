package qtx.dubbo.provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;import qtx.dubbo.service.provider.AcBusinessService;

/**
 * <p>
 * 流程节点业务表 前端控制器
 * </p>
 *
 * @author qtx
 * @since 2023-03-30
 */
@RestController
@RequestMapping("/acBusiness")
public class AcBusinessController {

  public final AcBusinessService acBusinessService;

  public AcBusinessController(AcBusinessService acBusinessService) {
    this.acBusinessService = acBusinessService;
  }

  @GetMapping("/list")
  public Object list() {
    return acBusinessService.list();
  }
}
