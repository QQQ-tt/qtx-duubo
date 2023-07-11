package qtx.dubbo.model.entity.provider;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import qtx.dubbo.model.base.BaseEntity;

import java.io.Serializable;

/**
 * 流程节点业务表
 *
 * @author qtx
 * @since 2023-03-30
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("ac_business")
public class AcBusiness extends BaseEntity implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("ac_node_id")
    private Integer acNodeId;

    @TableField("ac_name_id")
    private Integer acNameId;

    @TableField("business_info")
    private String businessInfo;
}
