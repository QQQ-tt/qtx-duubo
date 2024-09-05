package qtx.dubbo.easyexcel.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import qtx.dubbo.easyexcel.bo.ExcelVO;
import qtx.dubbo.easyexcel.config.ConvertList;
import qtx.dubbo.easyexcel.config.DataListener;
import qtx.dubbo.java.enums.DataEnums;
import qtx.dubbo.java.exception.DataException;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qtx
 * @since 2022/10/30 20:03
 */
@Slf4j
@Data
public class ExcelTransfer {

    /**
     * 上传excel 对用实体类不允许使用链式调用注解
     *
     * <p>@Accessors(chain = true) 对应实体类实现equals和hashCode方法会自动过滤与数据库重复的数据
     *
     * @param file    文件
     * @param service 对应实体的service
     * @return 成功与否
     */
    public static <T> boolean importExcel(MultipartFile file, IService<T> service, Class<T> aClass) {
        isEmpty(file);
        try {
            DataListener<T, T> listener = new DataListener<>(service, aClass);
            EasyExcel.read(file.getInputStream(), aClass, listener)
                    .sheet()
                    .doRead();
            if (StringUtils.isNotBlank(listener.errorString)) {
                return false;
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    /**
     * 上传excel 对用实体类不允许使用链式调用注解 自定义格式转换
     *
     * <p>@Accessors(chain = true) 对应实体类实现equals和hashCode方法会自动过滤与数据库重复的数据
     *
     * @param file    文件
     * @param service 对应实体的service
     * @param list    实现自定义转换方法
     * @return 成功与否
     */
    public static <I, O> String importExcel(MultipartFile file, IService<O> service, Class<I> aClass,
                                            ConvertList<I, O> list) {
        isEmpty(file);
        try {
            DataListener<I, O> listener = new DataListener<>(service, aClass, list);
            EasyExcel.read(file.getInputStream(), aClass, listener)
                    .sheet()
                    .doRead();
            if (StringUtils.isNotBlank(listener.errorString)) {
                return listener.errorString;
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return "false";
        }
        return "true";
    }

    /**
     * 多sheet，自定义数据源加表头
     *
     * @param response    http
     * @param name        文件名称
     * @param listListMap 表头为key，数据为value
     */
    public static void exportExcel(HttpServletResponse response, String name,
                                   Map<List<List<String>>, List<List<String>>> listListMap) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode(name, "UTF-8")
                .replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream())
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .build()) {
            AtomicInteger i = new AtomicInteger(0);
            listListMap.forEach((k, v) -> {
                WriteSheet writeSheet = EasyExcel.writerSheet(i.get(), "模板" + i)
                        .head(k)
                        .build();
                excelWriter.write(v, writeSheet);
                i.getAndIncrement();
            });
        }
    }

    /**
     * 下载excel
     *
     * @param response http
     * @param list     需要下载的数据
     * @param name     文件名称
     * @param sheet    表名
     */
    public static <I> void exportExcel(HttpServletResponse response, List<I> list, String name, String sheet,
                                       Class<I> aClass) {
        export(response, list, name, sheet, aClass);
    }

    public static <I> void exportExcel(HttpServletResponse response, List<I> list, String name, String sheet,
                                       List<String> headerList) {
        export10(response, list, name, sheet, headerList);
    }

    private static <T> void export10(HttpServletResponse response, List<T> list, String name, String sheet,
                                     List<String> headerList) {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode(name, "UTF-8")
                    .replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream())
                    .sheet(sheet)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .head(getHead(headerList))
                    .doWrite(list);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private static <T> void export(HttpServletResponse response, List<T> list, String name, String sheet, Class<?> aClass) {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode(name, "UTF-8")
                    .replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), aClass)
                    .sheet(sheet)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .doWrite(list);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 下载excel，多实体，多sheet
     *
     * @param response http
     * @param name     文件名称
     * @param list     实体:数据
     */
    public static void exportExcel(HttpServletResponse response, String name, List<ExcelVO> list) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode(name, "UTF-8")
                .replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream())
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .build()) {
            AtomicInteger i = new AtomicInteger(0);
            list.forEach(e -> {
                WriteSheet writeSheet = EasyExcel.writerSheet(i.get(), e.getSheet())
                        .head(e.getListsHead())
                        .build();
                i.getAndIncrement();
                excelWriter.write(e.getListsData(), writeSheet);
            });
        }
    }

    public static void exportExcel(HttpServletResponse response, String name, ExcelVO excelVO) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode(name, "UTF-8")
                .replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream())
                .registerWriteHandler(new SimpleColumnWidthStyleStrategy(10))
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .build()) {
            String sheet = excelVO.getSheet();
            WriteSheet writeSheet = EasyExcel.writerSheet(sheet)
                    .head(excelVO.getListsHead())
                    .build();
            excelWriter.write(excelVO.getListsData(), writeSheet);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static List<List<String>> getHead(List<String> headList) {
        List<List<String>> head = new ArrayList<>();
        headList.forEach(e -> {
            List<String> list = new ArrayList<>();
            list.add(e);
            head.add(list);
        });
        return head;
    }

    private static void isEmpty(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            new DataException(DataEnums.DATA_IS_ABNORMAL);
        }
    }
}
