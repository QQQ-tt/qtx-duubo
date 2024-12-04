package qtx.dubbo.service.log;

import com.baomidou.mybatisplus.extension.service.IService;
import qtx.dubbo.model.entity.log.SysLog;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author qtx
 * @since 2023-07-27
 */
public interface SysLogService extends IService<SysLog> {
    void test();
}
