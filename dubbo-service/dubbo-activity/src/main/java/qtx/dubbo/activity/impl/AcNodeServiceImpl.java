package qtx.dubbo.activity.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import qtx.dubbo.activity.mapper.AcNodeMapper;
import qtx.dubbo.model.entity.activity.AcNode;
import qtx.dubbo.service.activity.AcNodeService;

/**
 * <p>
 * 流程节点表 服务实现类
 * </p>
 *
 * @author qtx
 * @since 2023-07-26
 */
@Service
@DubboService(version = "1.0.0")
public class AcNodeServiceImpl extends ServiceImpl<AcNodeMapper, AcNode> implements AcNodeService {


}
