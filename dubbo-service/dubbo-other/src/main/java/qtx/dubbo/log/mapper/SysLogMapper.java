package qtx.dubbo.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import qtx.dubbo.model.entity.log.SysLog;

/**
 * <p>
 * 系统日志 Mapper 接口
 * </p>
 *
 * @author qtx
 * @since 2023-07-27
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {

}
