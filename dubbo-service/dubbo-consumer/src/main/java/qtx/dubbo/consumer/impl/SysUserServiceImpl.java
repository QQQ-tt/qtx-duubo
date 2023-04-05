package qtx.dubbo.consumer.impl;

import org.apache.dubbo.config.annotation.DubboReference;
import qtx.dubbo.model.entity.consumer.SysUser;
import qtx.dubbo.consumer.mapper.SysUserMapper;
import qtx.dubbo.model.entity.provider.AcBusiness;import qtx.dubbo.service.consumer.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;import qtx.dubbo.service.provider.AcBusinessService;import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author qtx
 * @since 2023-04-05
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

  @DubboReference private AcBusinessService acBusinessService;

  @Override
  public List<AcBusiness> listAc() {
    return acBusinessService.list();
  }

  @Override
  public String test() {
    return acBusinessService.test();
  }
}
