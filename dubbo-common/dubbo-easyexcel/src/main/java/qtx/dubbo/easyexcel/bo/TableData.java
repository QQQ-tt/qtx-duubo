package qtx.dubbo.easyexcel.bo;

import lombok.Data;

import java.util.List;

/**
 * @author qtx
 * @since 2023/2/22 15:07
 */
@Data
public class TableData {

    private List<List<String>> header;

    private List<List<Object>> body;

    private String tableName;

    public TableData(List<List<String>> header, List<List<Object>> body, String tableName) {
        this.header = header;
        this.body = body;
        this.tableName = tableName;
    }

}

