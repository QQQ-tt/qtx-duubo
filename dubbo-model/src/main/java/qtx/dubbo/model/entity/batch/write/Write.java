package qtx.dubbo.model.entity.batch.write;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.springframework.stereotype.Component;
import qtx.dubbo.model.base.BaseEntity;

import java.io.Serializable;

/**
 * 写
 *
 * @author qtx
 * @since 2023-03-30
 */
@Getter
@Setter
@Builder
@Component
@AllArgsConstructor
@NoArgsConstructor
@TableName("write")
public class Write extends BaseEntity implements Serializable {

    @TableField("name")
    private String name;

    @TableField("code")
    private String code;
}
