package qtx.dubbo.batch.mapper.master;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import qtx.dubbo.model.entity.batch.master.Master;

import java.util.List;

/**
 * <p>
 * 读 Mapper 接口
 * </p>
 *
 * @author qtx
 * @since 2023-03-30
 */
@Mapper
public interface MasterMapper extends BaseMapper<Master> {

    List<Master> selectRead();
}
