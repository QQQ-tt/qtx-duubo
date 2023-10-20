package qtx.dubbo.batch.mapper.write;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import qtx.dubbo.model.entity.batch.write.Write;

/**
 * <p>
 * 写 Mapper 接口
 * </p>
 *
 * @author qtx
 * @since 2023-03-30
 */
@Mapper
public interface WriteMapper extends BaseMapper<Write> {

    int inertWrite(Write item);

}
