package qtx.dubbo.model.entity.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import qtx.dubbo.model.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 系统日志
 * </p>
 *
 * @author qtx
 * @since 2023-07-27
 */
@Getter
@Setter
@TableName("sys_log")
public class SysLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户信息
     */
    @TableField("user_code")
    private String userCode;

    /**
     * 方法类型
     */
    @TableField("method")
    private String method;

    /**
     * 路径
     */
    @TableField("path")
    private String path;

    /**
     * 参数1
     */
    @TableField("json")
    private String json;

    /**
     * 参数2
     */
    @TableField("param")
    private String param;
}
