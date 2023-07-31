package qtx.dubbo.model.vo.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author qtx
 * @since 2023/3/17 20:20
 */
@Data
public class AcToDoVO {

    @Schema(description = "流程名称")
    private String acName;

    @Schema(description = "待办数量")
    private Integer num;
}
