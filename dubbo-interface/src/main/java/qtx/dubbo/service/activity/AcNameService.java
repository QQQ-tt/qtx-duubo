package qtx.dubbo.service.activity;

import qtx.dubbo.model.dto.activity.ActivityDTO;
import qtx.dubbo.model.entity.activity.AcName;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 单一流程名称表 服务类
 * </p>
 *
 * @author qtx
 * @since 2023-07-26
 */
public interface AcNameService extends IService<AcName> {

    /**
     * 创建或修改流程
     *
     * @param dto 流程详情
     * @return 流程uuid
     */
    String saveOrUpdateAc(ActivityDTO dto);
}
