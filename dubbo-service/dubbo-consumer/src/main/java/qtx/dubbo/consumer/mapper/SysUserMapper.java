package qtx.dubbo.consumer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import qtx.dubbo.model.entity.consumer.SysUser;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author qtx
 * @since 2023-04-05
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}
