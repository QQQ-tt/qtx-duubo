package qtx.dubbo.model.dto.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author qtx
 * @since 2023/3/17 11:26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcStartUpdateDTO {

    @NotNull
    @Schema(description = "任务uuid")
    private String taskUuid;

    @NotNull
    @Schema(description = "本次选择结果")
    private Boolean thisFlag;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "状态详情")
    private String statusInfo;

    @Schema(description = "初始化当前进度")
    private Boolean initializeNode;

    @Schema(description = "初始化当前流程")
    private Boolean initializeAc;

    @Schema(description = "文件uuid")
    private String fileUuid;

    @Schema(description = "备注")
    private String remark;
}
