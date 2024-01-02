package qtx.dubbo.model.dto.activity;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import qtx.dubbo.java.info.StaticConstant;

import java.util.List;

/**
 * @author qtx
 * @since 2023/3/11 10:57
 */
@Data
public class ActivityDTO {

    private Integer id;

    @NotNull
    @Size(max = StaticConstant.STRING_MAX_SIZE, message = StaticConstant.STRING_SIZ_ERROR)
    @Schema(description = "审批流名称", requiredMode = Schema.RequiredMode.AUTO)
    private String name;

    @Schema(description = "初始化类型", requiredMode = Schema.RequiredMode.AUTO)
    private Boolean initType;

    @NotNull
    @Size(max = StaticConstant.STRING_MAX_SIZE, message = StaticConstant.STRING_SIZ_ERROR)
    @Schema(description = "实际业务含义", requiredMode = Schema.RequiredMode.AUTO)
    private String businessMean;

    @NotNull
    @Size(max = StaticConstant.STRING_MAX_SIZE, message = StaticConstant.STRING_SIZ_ERROR)
    @Schema(description = "实际业务表名称")
    private String tableName;

    @Schema(description = "节点")
    private List<ActivityNodeDTO> list;
}
