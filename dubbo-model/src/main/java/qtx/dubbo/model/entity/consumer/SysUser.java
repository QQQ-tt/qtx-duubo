package qtx.dubbo.model.entity.consumer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import qtx.dubbo.model.base.BaseEntity;

import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author qtx
 * @since 2023-04-05
 */
@Getter
@Setter
@TableName("sys_user")
public class SysUser extends BaseEntity implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 工号
     */
    @TableField("user_code")
    private String userCode;

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
