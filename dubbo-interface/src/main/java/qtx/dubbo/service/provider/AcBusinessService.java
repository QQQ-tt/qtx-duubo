package qtx.dubbo.service.provider;

import qtx.dubbo.model.entity.provider.AcBusiness;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

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
   *
   * @return 返回值
   */
  Map<String, Object> test(Map<String,Object> map);

  AcBusiness test1(boolean flag);

}
