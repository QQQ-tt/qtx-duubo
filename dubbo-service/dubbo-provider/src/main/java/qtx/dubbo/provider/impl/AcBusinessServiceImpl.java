package qtx.dubbo.provider.impl;

import qtx.dubbo.model.entity.provider.AcBusiness;
import qtx.dubbo.provider.mapper.AcBusinessMapper;
import qtx.dubbo.service.provider.AcBusinessService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 流程节点业务表 服务实现类
 * </p>
 *
 * @author qtx
 * @since 2023-03-30
 */
@Service
public class AcBusinessServiceImpl extends ServiceImpl<AcBusinessMapper, AcBusiness> implements AcBusinessService {

}
