package qtx.dubbo.service.activity;

import qtx.dubbo.model.dto.activity.AcStartUpdateDTO;
import qtx.dubbo.model.entity.activity.AcStart;
import com.baomidou.mybatisplus.extension.service.IService;
import qtx.dubbo.model.vo.activity.AcToDoVO;
import qtx.dubbo.model.vo.activity.TaskVO;

import java.util.List;

/**
 * <p>
 * 流程启动表 服务类
 * </p>
 *
 * @author qtx
 * @since 2023-07-26
 */
public interface AcStartService extends IService<AcStart> {

    /**
     * 开启流程
     *
     * @param acUuid 流程uuid
     * @return 任务uuid
     */
    String startAc(String acUuid);

    /**
     * 更新节点状态
     *
     * @param dto 节点详情
     * @return true or false
     */
    boolean updateAc(AcStartUpdateDTO dto);

    /**
     * 用户待办
     *
     * @param acUuid 流程uuid
     * @param userCard 用户账户
     * @return 待办集合
     */
    List<AcToDoVO> toDo(String acUuid, String userCard);

    /**
     * 流程查询
     *
     * @param taskUuid 任务uuid
     * @return 流程集合
     */
    List<TaskVO> listTask(String taskUuid);
}
