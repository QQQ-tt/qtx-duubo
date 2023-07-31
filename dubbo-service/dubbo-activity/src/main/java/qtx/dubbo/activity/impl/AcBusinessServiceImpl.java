package qtx.dubbo.activity.impl;

import org.apache.dubbo.config.annotation.DubboService;
import qtx.dubbo.model.entity.activity.AcBusiness;
import qtx.dubbo.activity.mapper.AcBusinessMapper;
import qtx.dubbo.service.activity.AcBusinessService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 流程节点业务表 服务实现类
 * </p>
 *
 * @author qtx
 * @since 2023-07-26
 */
@Service
@DubboService(version = "1.0.0")
public class AcBusinessServiceImpl extends ServiceImpl<AcBusinessMapper, AcBusiness> implements AcBusinessService {

}
