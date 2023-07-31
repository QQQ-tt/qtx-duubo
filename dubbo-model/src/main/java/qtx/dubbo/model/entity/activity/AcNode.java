package qtx.dubbo.model.entity.activity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.*;
import qtx.dubbo.model.base.BaseEntity;

/**
 * <p>
 * 流程节点表
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
@TableName("ac_node")
public class AcNode extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("ac_name_id")
    private Integer acNameId;

    /**
     * 节点名称
     */
    @TableField("node_name")
    private String nodeName;

    /**
     * 节点组编号
     */
    @TableField("node_group")
    private Integer nodeGroup;

    /**
     * 当前节点通过个数
     */
    @TableField("node_pass_num")
    private Integer nodePassNum;

    /**
     * 是否隐藏
     */
    @TableField("is_hidden")
    private Boolean hidden;

    /**
     * 是否为基础节点
     */
    @TableField("node_type")
    private Boolean nodeType;

    /**
     * 节点对应的流程uuid
     */
    @TableField("ac_name_uuid")
    private String acNameUuid;
}
