package qtx.dubbo.model.vo.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author qtx
 * @since 2023/3/17 20:40
 */
@Data
public class TaskVO {
    @JsonIgnore
    private String acUuid;
    @JsonIgnore
    private String acNameUuid;
    @JsonIgnore
    private Boolean pFlag;

    @Schema(description = "节点名称")
    private String nodeName;

    @Schema(description = "节点组编号")
    private Integer nodeGroup;

    @Schema(description = "业务名称")
    private String business;

    @Schema(description = "提交时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime submissionTime;

    @Schema(description = "通过时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime passTime;

    @Schema(description = "本节点结果")
    private Boolean flag;

    @Schema(description = "本次操作结果")
    private Boolean thisFlag;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "状态详情")
    private String statusInfo;

    private List<TaskVO> list;
}
