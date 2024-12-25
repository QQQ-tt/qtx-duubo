package qtx.dubbo.model.entity.provider;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import qtx.dubbo.model.base.BaseEntity;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author qtx
 * @since 2024-12-25
 */
@Getter
@Setter
@TableName("sys_user")
public class SysUser extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 账户
     */
    @TableField("user_card")
    private String userCard;

    /**
     * 用户名称
     */
    @TableField("user_name")
    private String userName;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 启用状态
     */
    @TableField("status")
    private Boolean status;
}
