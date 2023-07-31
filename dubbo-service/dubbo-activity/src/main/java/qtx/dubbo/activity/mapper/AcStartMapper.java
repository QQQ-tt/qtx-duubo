package qtx.dubbo.activity.mapper;

import org.apache.ibatis.annotations.Param;
import qtx.dubbo.model.bo.activity.AcBO;
import qtx.dubbo.model.entity.activity.AcStart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import qtx.dubbo.model.vo.activity.AcToDoVO;
import qtx.dubbo.model.vo.activity.TaskVO;

import java.util.List;

/**
 * <p>
 * 流程启动表 Mapper 接口
 * </p>
 *
 * @author qtx
 * @since 2023-07-26
 */
@Mapper
public interface AcStartMapper extends BaseMapper<AcStart> {

    /**
     * 查询流程ac
     *
     * @param acUuid 流程id
     * @param flag 初始化类型
     * @param group 组编号
     * @return 初始流程集合
     */
    List<AcBO> selectAc(
            @Param("acUuid") String acUuid, @Param("flag") Boolean flag, @Param("group") Integer group);

    /**
     * 查询待办
     *
     * @param acUuid 流程uuid
     * @param userCard 用户账户
     * @return 待办集合
     */
    List<AcToDoVO> selectToDo(@Param("acUuid") String acUuid, @Param("userCard") String userCard);

    /**
     * 查询流程详情
     *
     * @param taskUuid 任务uuid
     * @return 流程集合
     */
    List<TaskVO> selectTask(@Param("taskUuid") String taskUuid);
}
