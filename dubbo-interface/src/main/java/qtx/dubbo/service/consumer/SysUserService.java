package qtx.dubbo.service.consumer;

import qtx.dubbo.model.entity.consumer.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import qtx.dubbo.model.entity.provider.AcBusiness;
import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author qtx
 * @since 2023-04-05
 */
public interface SysUserService extends IService<SysUser> {
  /**
   * 远程测试
   * @return 结果
   */
  List<AcBusiness> listAc();

  /**
   * 测试
   * @return 返回值
   */
  String test();
}
