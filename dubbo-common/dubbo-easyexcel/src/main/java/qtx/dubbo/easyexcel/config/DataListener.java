package qtx.dubbo.easyexcel.config;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author qtx
 * @since 2022/10/30 20:00
 */
@Slf4j
public class DataListener<E> implements ReadListener<E> {
    /** 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收 */
    private static final int BATCH_COUNT = 10000;
    /** 缓存的数据 */
    private List<E> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    /** 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。 */
    private final IService<E> service;
    /** excel数据与table不一致，手动实现转换方法 */
    private ConvertList<E> convert;
    /**
     * 错误标识
     */
    private boolean stringError;
    /**
     * 导入实体
     */
    private final Class<?> entity;

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param service
     */
    public DataListener(IService<E> service, Class<?> aClass) {
        this.service = service;
        this.entity = aClass;
    }

    /**
     * 需要转换excel数据，请使用这个构造方法。
     *
     * @param service
     * @param convert
     */
    public DataListener(IService<E> service, ConvertList<E> convert, Class<?> aClass) {
        this.service = service;
        this.convert = convert;
        this.entity = aClass;
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(E data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        try {
            if (isNull(data)) {
                log.info("添加一条数据到备选集合:{}", JSON.toJSONString(data));
                cachedDataList.add(data);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        Set<String> head;
        try {
            head = excelHead();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Set<String> finalHead = head;
        headMap.forEach((k, v) -> {
            if (!finalHead.contains(v.getStringValue())) {
                stringError = false;
            }
        });
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    @Override
    public boolean hasNext(AnalysisContext context) {
        return true;
    }

    /** 加上存储数据库 */
    private void saveData() {
        List<E> saveList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        Set<E> asList;
        if (convert != null) {
            log.info("{}条数据，开始转换数据格式。", cachedDataList.size());
            saveList.addAll(new HashSet<>(convert.convert(cachedDataList)));
        } else {
            Set<E> set = new HashSet<>(service.query().eq("delete_flag", "0").list());
            asList = new HashSet<>(cachedDataList);
            asList.forEach(s -> {
                if (!set.contains(s)) {
                    saveList.add(s);
                }
            });
            log.info("{}条数据，被过滤掉！", cachedDataList.size() - saveList.size());
        }
        log.info("{}条数据，开始存储数据库！", saveList.size());
        service.saveBatch(saveList);
        log.info("存储数据库成功！");
    }

    /**
     * 过滤掉空数据
     *
     * @param data 实体类
     * @return 是否为空
     *
     * @throws IllegalAccessException
     */
    private boolean isNull(E data) throws IllegalAccessException {
        Class<?> aClass = data.getClass();
        Field[] fields = aClass.getDeclaredFields();
        int num = 0;
        for (Field field : fields) {
            if ("serialVersionUID".equals(field.getName())) {
                num++;
                continue;
            }
            field.setAccessible(true);
            Object o = field.get(data);
            if (Objects.isNull(o)) {
                num++;
            }
        }
        return num != fields.length;
    }

    public Set<String> excelHead() throws ClassNotFoundException {
        HashSet<String> set = new HashSet<>(20);
        Field[] fields = entity.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ExcelProperty.class)) {
                String[] value = field.getAnnotation(ExcelProperty.class).value();
                set.add(value[value.length - 1]);
            }
        }
        return set;
    }
}
