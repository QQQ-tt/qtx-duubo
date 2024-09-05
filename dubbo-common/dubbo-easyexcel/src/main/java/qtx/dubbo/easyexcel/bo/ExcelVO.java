package qtx.dubbo.easyexcel.bo;

import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author qtx
 * @since 2022/9/8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcelVO {
    private String sheet;

    @ColumnWidth(20)
    private List<List<String>> listsHead;
    private List<List<Object>> listsData;
}
