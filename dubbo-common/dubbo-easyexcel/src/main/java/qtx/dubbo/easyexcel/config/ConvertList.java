package qtx.dubbo.easyexcel.config;

import java.util.List;

/**
 * @author qtx
 * @since 2022/10/30 20:05
 */
@FunctionalInterface
public interface ConvertList<I, O> {

    /**
     * excel与数据库不一致，手动实现转换方式
     *
     * @param list 初始集合
     * @return 处理后集合
     */
    List<O> convert(List<I> list);
}
