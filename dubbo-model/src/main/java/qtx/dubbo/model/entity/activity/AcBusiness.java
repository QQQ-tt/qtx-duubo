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
 * 流程节点业务表
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
@TableName("ac_business")
public class AcBusiness extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("ac_node_id")
    private Integer acNodeId;

    @TableField("ac_name_id")
    private Integer acNameId;

    /**
     * 实际关联业务(处理人或角色)
     */
    @TableField("business_info")
    private String businessInfo;
}
