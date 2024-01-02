package qtx.dubbo.log.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import qtx.dubbo.log.mapper.SysLogMapper;
import qtx.dubbo.model.entity.log.SysLog;
import qtx.dubbo.service.log.SysLogService;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author qtx
 * @since 2023-07-27
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

}
