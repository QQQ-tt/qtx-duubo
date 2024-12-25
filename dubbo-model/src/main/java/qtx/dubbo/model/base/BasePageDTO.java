package qtx.dubbo.model.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author qtx
 * @since 2024/12/25 21:57
 */
@Data
public class BasePageDTO<T> {

    private int pageSize;

    private int pageNo;

    @JsonIgnore
    public Page<T> getPage() {
        return PageDTO.of(pageNo, pageSize);
    }
}
