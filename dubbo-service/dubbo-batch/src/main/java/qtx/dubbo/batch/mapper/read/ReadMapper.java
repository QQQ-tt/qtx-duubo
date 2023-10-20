package qtx.dubbo.batch.mapper.read;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import qtx.dubbo.model.entity.batch.read.Read;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 读 Mapper 接口
 * </p>
 *
 * @author qtx
 * @since 2023-03-30
 */
@Mapper
public interface ReadMapper extends BaseMapper<Read> {

    List<Read> selectRead(Map<String, Object> map);
}
