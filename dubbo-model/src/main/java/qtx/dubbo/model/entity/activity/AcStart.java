package qtx.dubbo.model.entity.activity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import qtx.dubbo.model.base.BaseEntity;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 流程启动表
 * </p>
 *
 * @author qtx
 * @since 2023-07-26
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("ac_start")
public class AcStart extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 流程名称
     */
    @TableField("ac_name")
    private String acName;

    /**
     * 节点名称
     */
    @TableField("ac_node")
    private String acNode;

    /**
     * 节点组编号
     */
    @TableField("ac_node_group")
    private Integer acNodeGroup;

    /**
     * 实际关联业务(处理人或角色)
     */
    @TableField("ac_business")
    private String acBusiness;

    /**
     * 流程uuid
     */
    @TableField("ac_uuid")
    private String acUuid;

    /**
     * 流程任务uuid
     */
    @TableField("task_uuid")
    private String taskUuid;

    /**
     * 流程任务节点uuid
     */
    @TableField("task_node_uuid")
    private String taskNodeUuid;

    /**
     * 父流程任务节点uuid
     */
    @TableField("parent_task_node_uuid")
    private String parentTaskNodeUuid;

    /**
     * 提交日期
     */
    @TableField("submission_time")
    private LocalDateTime submissionTime;

    /**
     * 审核进度
     */
    @TableField("review_progress")
    private BigDecimal reviewProgress;

    /**
     * 审核通过日期
     */
    @TableField("pass_time")
    private LocalDateTime passTime;

    /**
     * 当前节点结果
     */
    @TableField("flag")
    private Boolean flag;

    /**
     * 当前节点的本次操作结果
     */
    @TableField("this_flag")
    private Boolean thisFlag;

    /**
     * 当前节点所需通过总数
     */
    @TableField("node_pass_num")
    private Integer nodePassNum;

    /**
     * 当前节点已通过数量
     */
    @TableField("this_node_pass_num")
    private Integer thisNodePassNum;

    /**
     * 审核状态
     */
    @TableField("status")
    private String status;

    /**
     * 审核状态详情
     */
    @TableField("status_info")
    private String statusInfo;

    /**
     * 节点是否开启
     */
    @TableField("is_node")
    private Boolean node;

    /**
     * 是否隐藏
     */
    @TableField("is_hidden")
    private Boolean hidden;

    /**
     * 是否为历史记录
     */
    @TableField("is_his")
    private Boolean his;

    /**
     * 文件uuid
     */
    @TableField("file_uuid")
    private String fileUuid;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
}
