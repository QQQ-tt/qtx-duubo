package qtx.dubbo.easyexcel.config;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.extern.slf4j.Slf4j;
import qtx.dubbo.easyexcel.config.ConvertList;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author qtx
 * @since 2022/4/27
 */
@Slf4j
public class DataListener<I, O> implements ReadListener<I> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 10000;
    /**
     * 缓存的数据
     */
    private List<O> cachedDataListO = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    private List<I> cachedDataListI = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private final IService<O> service;

    /**
     * 导入表头实体
     */
    private final Class<I> aClass;

    /**
     * excel数据与table不一致，手动实现转换方法
     */
    private ConvertList<I, O> convert;

    /**
     * 错误字符
     */
    public String errorString;

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param service 存储业务层
     * @param aClass  传入excel实体类
     */
    public DataListener(IService<O> service, Class<I> aClass) {
        this.service = service;
        this.aClass = aClass;
    }

    /**
     * 需要转换excel数据，请使用这个构造方法。
     *
     * @param service 存储业务层
     * @param aClass  传入excel实体类
     * @param convert 对象转换
     */
    public DataListener(IService<O> service, Class<I> aClass, ConvertList<I, O> convert) {
        this.service = service;
        this.aClass = aClass;
        this.convert = convert;
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) {
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data one row value. Isis same as {@link AnalysisContext#readRowHolder()}
     */
    @Override
    public void invoke(I data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        try {
            if (isNull(data)) {
                log.info("添加一条数据到备选集合:{}", JSON.toJSONString(data));
                if (convert != null) {
                    cachedDataListI.add(data);
                } else {
                    cachedDataListO.add((O) data);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataListI.size() + cachedDataListO.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataListI = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
            cachedDataListO = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
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
                errorString = "表头不符合规则";
            }
        });
    }

    /**
     * 所有数据解析完成了 都会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        List<O> saveList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        if (convert != null) {
            log.info("{}条数据，开始转换数据格式。", cachedDataListI.size() + cachedDataListO.size());
            saveList.addAll(new HashSet<>(convert.convert(cachedDataListI)));
        } else {
            Set<O> set = new HashSet<>(service.query()
                    .eq("deleted", "0")
                    .list());
            Set<O> asList = new HashSet<>(cachedDataListO);
            asList.forEach(s -> {
                if (!set.contains(s)) {
                    saveList.add(s);
                }
            });
            log.info("{}条数据，被过滤掉！", cachedDataListO.size() - saveList.size());
        }
        if (saveList.isEmpty()) {
            errorString = "导入数据为空";
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
     */
    private boolean isNull(I data) throws IllegalAccessException {
        Class<?> aClass = data.getClass();
        Field[] fields = aClass.getDeclaredFields();
        int num = 0;
        AccessibleObject.setAccessible(fields, true);
        for (Field field : fields) {
            if ("serialVersionUID".equals(field.getName())) {
                num++;
                continue;
            }
            Object o = field.get(data);
            if (Objects.isNull(o)) {
                num++;
            }
        }
        return num != fields.length;
    }

    public Set<String> excelHead() throws ClassNotFoundException {
        HashSet<String> set = new HashSet<>(30);
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ExcelProperty.class)) {
                String[] value = field.getAnnotation(ExcelProperty.class)
                        .value();
                set.add(value[value.length - 1]);
            }
        }
        return set;
    }
}
