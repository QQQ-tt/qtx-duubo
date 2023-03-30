package qtx.dubbo.model.base;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author QTX
 * @since 2022/8/30
 */
@Data
public class BaseEntity {

  @JsonIgnore
  @ExcelProperty("创建数据的用户")
  @TableField(fill = FieldFill.INSERT)
  private String createBy;

  @JsonIgnore
  @ExcelIgnore
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createOn;

  @JsonIgnore
  @ExcelProperty("修改数据的用户")
  @TableField(fill = FieldFill.UPDATE)
  private String updateBy;

  @JsonIgnore
  @ExcelIgnore
  @TableField(fill = FieldFill.UPDATE)
  private LocalDateTime updateOn;

  @JsonIgnore @ExcelIgnore private Integer deleteFlag;
}
