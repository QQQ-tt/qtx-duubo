package qtx.dubbo.service.provider;

import qtx.dubbo.model.entity.provider.AcBusiness;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 流程节点业务表 服务类
 * </p>
 *
 * @author qtx
 * @since 2023-03-30
 */
public interface AcBusinessService extends IService<AcBusiness> {
  /**
   * 测试
   * @return 返回值
   */
  String test();

}
