package qtx.dubbo.model.dto.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.util.Set;

/**
 * @author qtx
 * @since 2023/3/11 11:00
 */
@Data
public class ActivityNodeDTO {

    @NotNull
    @Schema(description = "节点名称")
    private String nodeName;

    @NotNull
    @Schema(description = "节点组编号")
    private Integer nodeGroup;

    @Schema(description = "当前节点组所需通过数量")
    private Integer nodePassNum;

    @Schema(description = "节点是否隐藏")
    private Boolean hidden;

    @Schema(description = "节点类型，是否为基础节点")
    private Boolean nodeType;

    @Schema(description = "节点对应的流程uuid")
    private String acNameUuid;

    @Schema(description = "实际关联业务(处理人或角色等)")
    private Set<String> stringSet;
}
