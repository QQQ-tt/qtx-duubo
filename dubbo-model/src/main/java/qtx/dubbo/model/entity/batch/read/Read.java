package qtx.dubbo.model.entity.batch.read;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import qtx.dubbo.model.base.BaseEntity;

import java.io.Serializable;

/**
 * 读
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
@TableName("read")
public class Read extends BaseEntity implements Serializable {

    @TableField("name")
    private String name;

    @TableField("code")
    private String code;
}
