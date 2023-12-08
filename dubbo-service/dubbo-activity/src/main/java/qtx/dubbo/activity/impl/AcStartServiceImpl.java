package qtx.dubbo.activity.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;
import qtx.dubbo.config.utils.NumUtils;
import qtx.dubbo.java.CommonMethod;
import qtx.dubbo.java.enums.DataEnums;
import qtx.dubbo.java.exception.DataException;
import qtx.dubbo.java.info.StaticConstant;
import qtx.dubbo.model.bo.activity.AcBO;
import qtx.dubbo.model.dto.activity.AcStartUpdateDTO;
import qtx.dubbo.model.entity.activity.AcName;
import qtx.dubbo.model.entity.activity.AcStart;
import qtx.dubbo.activity.mapper.AcStartMapper;
import qtx.dubbo.model.vo.activity.AcToDoVO;
import qtx.dubbo.model.vo.activity.TaskVO;
import qtx.dubbo.service.activity.AcNameService;
import qtx.dubbo.service.activity.AcStartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * <p>
 * 流程启动表 服务实现类
 * </p>
 *
 * @author qtx
 * @since 2023-07-26
 */
@Service
@DubboService(version = "1.0.0")
public class AcStartServiceImpl extends ServiceImpl<AcStartMapper, AcStart> implements AcStartService {

    private final CommonMethod commonMethod;

    private final AcNameService acNameService;

    public AcStartServiceImpl(AcNameService acNameService, CommonMethod commonMethod) {
        this.acNameService = acNameService;
        this.commonMethod = commonMethod;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String startAc(String acUuid) {
        String uuid = NumUtils.uuid();
        AcName one =
                acNameService.getOne(Wrappers.lambdaQuery(AcName.class)
                        .eq(AcName::getAcUuid, acUuid));
        if (one == null) {
            new DataException(DataEnums.DATA_AC_NULL);
        }
        List<AcBO> boList = baseMapper.selectAc(acUuid, one.getInitType(), null);
        initNode(true, boList, uuid, null);
        return uuid;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAc(AcStartUpdateDTO dto) {
        String user = commonMethod.getUserCode();
        // 查询当前节点
        AcStart one =
                getOne(
                        Wrappers.lambdaQuery(AcStart.class)
                                .eq(AcStart::getHis, Boolean.FALSE)
                                .eq(AcStart::getTaskUuid, dto.getTaskUuid())
                                .eq(AcStart::getNode, Boolean.TRUE)
                                .eq(AcStart::getAcBusiness, user)
                                .isNull(AcStart::getThisFlag)
                                .orderByAsc(AcStart::getAcNodeGroup)
                                .last("limit 1"));
        if (one == null) {
            new DataException(DataEnums.DATA_AC_NULL);
        }
        AcStart parent = null;
        // 判断此节点是否为根节点
        if (!StaticConstant.ACTIVITY_PARENT.equals(one.getParentTaskNodeUuid())) {
            parent =
                    getOne(
                            Wrappers.lambdaQuery(AcStart.class)
                                    .eq(AcStart::getTaskNodeUuid, one.getParentTaskNodeUuid()));
            update(
                    Wrappers.lambdaUpdate(AcStart.class)
                            .eq(AcStart::getId, parent.getId())
                            .set(AcStart::getHis, Boolean.TRUE));
        }
        AcStart acStart = getAcStart(dto, one);
        if (dto.getInitializeNode() != null && dto.getInitializeNode()) {
            // 初始化当前节点
            acStart.setReviewProgress(new BigDecimal(0));
            acStart.setThisNodePassNum(0);
        } else if (dto.getInitializeAc() != null && dto.getInitializeAc()) {
            // 初始化当前流程
            update(
                    Wrappers.lambdaUpdate(AcStart.class)
                            .eq(AcStart::getAcUuid, acStart.getAcUuid())
                            .set(AcStart::getHis, Boolean.TRUE));
            startAc(acStart.getAcUuid());
        } else {
            // 当前节点已满
            if (acStart.getThisNodePassNum()
                    .equals(acStart.getNodePassNum())) {
                acStart.setReviewProgress(new BigDecimal(1));
                AcName acName =
                        acNameService.getOne(
                                Wrappers.lambdaQuery(AcName.class)
                                        .eq(AcName::getAcUuid, acStart.getAcUuid()));
                if (acName.getInitType()) {
                    // 开启下一组节点
                    List<AcBO> list =
                            baseMapper.selectAc(acStart.getAcUuid(), Boolean.FALSE, acStart.getAcNodeGroup() + 1);
                    if (parent != null) {
                        parent.setAcBusiness(
                                Arrays.toString(list.stream()
                                                .map(AcBO::getBusinessInfo)
                                                .distinct()
                                                .toArray())
                                        .replace("[", "")
                                        .replace("]", ""));
                    }
                    if (!list.isEmpty()) {
                        initNode(false, list, acStart.getTaskUuid(), acStart.getParentTaskNodeUuid());
                    }
                }
                acStart.setFlag(Boolean.TRUE);
            } else {
                acStart.setReviewProgress(
                        new BigDecimal(acStart.getThisNodePassNum())
                                .divide(new BigDecimal(acStart.getNodePassNum()), 2, RoundingMode.HALF_DOWN));
            }
            // 父节点操作
            if (parent != null) {
                parent.setReviewProgress(
                        new BigDecimal(parent.getThisNodePassNum())
                                .divide(new BigDecimal(parent.getNodePassNum()), 2, RoundingMode.HALF_DOWN));
                parent.setId(null);
                parent.setThisFlag(dto.getThisFlag());
                if (acStart.getThisNodePassNum()
                        .equals(acStart.getNodePassNum())) {
                    parent.setFlag(Boolean.TRUE);
                }
                save(parent);
            }
        }
        return updateById(acStart);
    }

    @Override
    public List<AcToDoVO> toDo(String acUuid, String userCard) {
        return baseMapper.selectToDo(acUuid, userCard != null ? userCard : commonMethod.getUserCode());
    }

    @Override
    public List<TaskVO> listTask(String taskUuid) {
        List<TaskVO> list = baseMapper.selectTask(taskUuid);
        return list.stream()
                .filter(TaskVO::getPFlag)
                .peek(getAction(list))
                .sorted(Comparator.comparing(TaskVO::getNodeGroup))
                .collect(Collectors.toList());
    }

    private List<TaskVO> child(List<TaskVO> list, TaskVO vo) {
        return list.stream()
                .filter(f -> f.getAcUuid()
                        .equals(vo.getAcNameUuid()))
                .peek(getAction(list))
                .sorted(Comparator.comparing(TaskVO::getNodeGroup))
                .collect(Collectors.toList());
    }

    private Consumer<TaskVO> getAction(List<TaskVO> list) {
        return p -> {
            List<TaskVO> child = child(list, p);
            p.setList(child);
            if (!child.isEmpty()) {
                p.setBusiness(
                        Arrays.toString(child.stream()
                                        .map(TaskVO::getBusiness)
                                        .distinct()
                                        .toArray())
                                .replace("[", "")
                                .replace("]", ""));
            }
        };
    }


    private void initNode(boolean start, List<AcBO> list, String taskUuid, String pNodeUuid) {
        List<AcStart> acStarts = new ArrayList<>();
        addNode(start, list, taskUuid, pNodeUuid, acStarts);
        saveBatch(acStarts);
    }

    private void addNode(
            boolean start, List<AcBO> list, String taskUuid, String pNodeUuid, List<AcStart> acStarts) {
        list.forEach(
                bo -> {
                    boolean flag = true;
                    if (start) {
                        flag = bo.getNodeGroup() == 1;
                    }
                    String uuid = NumUtils.uuid();
                    List<AcBO> selectAc = null;
                    if (!bo.getNodeType()) {
                        selectAc = baseMapper.selectAc(bo.getAcNameUuid(), start, null);
                    }
                    int num = 0;
                    String businessInfo = "";
                    if (selectAc != null) {
                        num = selectAc.stream()
                                .mapToInt(AcBO::getNodePassNum)
                                .sum();
                        businessInfo =
                                Arrays.toString(selectAc.stream()
                                                .map(AcBO::getBusinessInfo)
                                                .distinct()
                                                .toArray())
                                        .replace("[", "")
                                        .replace("]", "");
                    }
                    acStarts.add(
                            AcStart.builder()
                                    .taskUuid(taskUuid)
                                    .taskNodeUuid(uuid)
                                    .parentTaskNodeUuid(pNodeUuid)
                                    .acUuid(bo.getAcUuid())
                                    .acName(bo.getName())
                                    .acNode(bo.getNodeName())
                                    .acNodeGroup(bo.getNodeGroup())
                                    .acBusiness(selectAc != null ? businessInfo : bo.getBusinessInfo())
                                    .node(flag ? Boolean.TRUE : Boolean.FALSE)
                                    .hidden(bo.getHidden())
                                    .submissionTime(flag ? LocalDateTime.now() : null)
                                    .nodePassNum(selectAc != null ? num : bo.getNodePassNum())
                                    .build());
                    if (selectAc != null) {
                        addNode(
                                start,
                                selectAc.stream()
                                        .filter(f -> f.getNodeGroup() == 1)
                                        .collect(Collectors.toList()),
                                taskUuid,
                                uuid,
                                acStarts);
                    }
                });
    }

    private AcStart getAcStart(AcStartUpdateDTO dto, AcStart one) {
        return AcStart.builder()
                .id(one.getId())
                .acName(one.getAcName())
                .acNode(one.getAcNode())
                .acNodeGroup(one.getAcNodeGroup())
                .acBusiness(one.getAcBusiness())
                .acUuid(one.getAcUuid())
                .parentTaskNodeUuid(one.getParentTaskNodeUuid())
                .taskUuid(one.getTaskUuid())
                .submissionTime(one.getSubmissionTime())
                .passTime(dto.getThisFlag() ? LocalDateTime.now() : null)
                .thisFlag(dto.getThisFlag())
                .status(dto.getStatus())
                .thisNodePassNum(
                        dto.getThisFlag() ? one.getThisNodePassNum() + 1 : one.getThisNodePassNum())
                .nodePassNum(one.getNodePassNum())
                .statusInfo(dto.getStatusInfo())
                .fileUuid(dto.getFileUuid())
                .remark(dto.getRemark())
                .node(one.getNode())
                .hidden(one.getHidden())
                .his(one.getHis())
                .build();
    }
}
