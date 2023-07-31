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
 * 单一流程名称表
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
@TableName("ac_name")
public class AcName extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 流程uuid
     */
    @TableField("ac_uuid")
    private String acUuid;

    /**
     * 流程名称
     */
    @TableField("name")
    private String name;

    /**
     * 是否逐一初始化
     */
    @TableField("init_type")
    private Boolean initType;

    /**
     * 业务含义
     */
    @TableField("business_mean")
    private String businessMean;

    /**
     * 实际业务表名称
     */
    @TableField("table_name")
    private String tableName;

    /**
     * 是否为历史记录 0:否 1:是
     */
    @TableField("is_history")
    private Boolean history;
}
