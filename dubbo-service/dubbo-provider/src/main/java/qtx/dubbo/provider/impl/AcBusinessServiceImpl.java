package qtx.dubbo.provider.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import qtx.dubbo.model.entity.provider.AcBusiness;
import qtx.dubbo.provider.mapper.AcBusinessMapper;
import qtx.dubbo.service.provider.AcBusinessService;

/**
 * <p>
 * 流程节点业务表 服务实现类
 * </p>
 *
 * @author qtx
 * @since 2023-03-30
 */
@Service
@DubboService
public class AcBusinessServiceImpl extends ServiceImpl<AcBusinessMapper, AcBusiness>
    implements AcBusinessService {
  @Override
  public String test() {
    return "provider";
  }
}
