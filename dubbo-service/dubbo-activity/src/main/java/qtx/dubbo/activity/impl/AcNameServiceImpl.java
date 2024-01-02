package qtx.dubbo.activity.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import qtx.dubbo.activity.mapper.AcNameMapper;
import qtx.dubbo.config.utils.NumUtils;
import qtx.dubbo.java.enums.DataEnums;
import qtx.dubbo.java.exception.DataException;
import qtx.dubbo.model.dto.activity.ActivityDTO;
import qtx.dubbo.model.entity.activity.AcBusiness;
import qtx.dubbo.model.entity.activity.AcName;
import qtx.dubbo.model.entity.activity.AcNode;
import qtx.dubbo.service.activity.AcBusinessService;
import qtx.dubbo.service.activity.AcNameService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 单一流程名称表 服务实现类
 * </p>
 *
 * @author qtx
 * @since 2023-07-26
 */
@Service
@DubboService(version = "1.0.0")
public class AcNameServiceImpl extends ServiceImpl<AcNameMapper, AcName> implements AcNameService {

    private final AcNodeServiceImpl acNodeService;

    private final AcBusinessService acBusinessService;

    public AcNameServiceImpl(AcNodeServiceImpl acNodeService, AcBusinessService acBusinessService) {
        this.acNodeService = acNodeService;
        this.acBusinessService = acBusinessService;
    }

    @Override
    public String saveOrUpdateAc(ActivityDTO dto) {
        // 流程主表创建
        String uuid;
        if (dto.getId() != null) {
            AcName oldAcName = getById(dto.getId());
            uuid = oldAcName.getAcUuid();
            update(
                    Wrappers.lambdaUpdate(AcName.class)
                            .set(AcName::getHistory, true)
                            .eq(AcName::getId, oldAcName.getId()));
        } else {
            uuid = NumUtils.uuid();
        }
        AcName buildAcName =
                AcName.builder()
                        .acUuid(uuid)
                        .name(dto.getName())
                        .initType(dto.getInitType())
                        .businessMean(dto.getBusinessMean())
                        .tableName(dto.getTableName())
                        .build();
        save(buildAcName);
        // 流程详情创建
        dto.getList()
                .forEach(
                        e -> {
                            boolean flag1 =
                                    e.getNodePassNum() == null
                                            && !e.getNodeType()
                                            && e.getStringSet()
                                            .isEmpty()
                                            && StringUtils.isNotBlank(e.getAcNameUuid());
                            boolean flag2 =
                                    e.getNodePassNum() != null
                                            && e.getNodeType()
                                            && StringUtils.isBlank(e.getAcNameUuid())
                                            && !e.getStringSet()
                                            .isEmpty();
                            boolean flag = !(flag1 || flag2);
                            if (flag) {
                                new DataException(DataEnums.DATA_IS_ABNORMAL);
                            }
                            if (e.getNodePassNum() != null && e.getStringSet()
                                    .size() < e.getNodePassNum()) {
                                new DataException(DataEnums.DATA_IS_ABNORMAL);
                            }
                            AcNode acNode =
                                    AcNode.builder()
                                            .acNameId(buildAcName.getId())
                                            .nodeName(e.getNodeName())
                                            .nodePassNum(e.getNodePassNum())
                                            .nodeGroup(e.getNodeGroup())
                                            .hidden(e.getHidden())
                                            .nodeType(e.getNodeType())
                                            .acNameUuid(e.getAcNameUuid())
                                            .build();
                            acNodeService.save(acNode);
                            List<AcBusiness> acBusinesses = new ArrayList<>();
                            e.getStringSet()
                                    .forEach(
                                            b ->
                                                    acBusinesses.add(
                                                            AcBusiness.builder()
                                                                    .acNameId(buildAcName.getId())
                                                                    .acNodeId(acNode.getId())
                                                                    .businessInfo(b)
                                                                    .build()));
                            acBusinessService.saveBatch(acBusinesses);
                        });
        return uuid;
    }
}
