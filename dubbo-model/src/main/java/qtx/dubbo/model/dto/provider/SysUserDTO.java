package qtx.dubbo.model.dto.provider;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import qtx.dubbo.model.base.BasePageDTO;
import qtx.dubbo.model.entity.provider.SysUser;

/**
 * @author qtx
 * @since 2024/12/25 21:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserDTO extends BasePageDTO<SysUser> {

    @Schema(description = "用户账户")
    private String userCard;

    @Schema(description = "用户名称")
    private String userName;
}
