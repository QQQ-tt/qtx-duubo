package qtx.dubbo.apisix;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.google.common.reflect.TypeToken;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import qtx.dubbo.log.impl.SysLogServiceImpl;
import qtx.dubbo.model.base.BaseEntity;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author qtx
 * @since 2023/8/26 17:43
 */
public class MybatisServiceTest {

    @SneakyThrows
    public <E extends BaseEntity> void test(IService<E> service) {
        BaseEntity base = new BaseEntity();
        Class<E> aClass = getClass(service);
        E one = service.getOne(Wrappers.lambdaQuery(aClass)
                .select(E::getDeleteFlag)
                .eq(E::getCreateOn, "1"));
        if (Objects.nonNull(one)) {
            base.setDeleteFlag(one.getDeleteFlag());
        }
        E e = aClass.getDeclaredConstructor()
                .newInstance();
        BeanUtils.copyProperties(base, e);
        service.save(e);
    }

    private <E extends BaseEntity> Class<E> getClass(IService<E> service) {
        TypeToken<E> typeToken = new TypeToken<E>(getClass()) {
        };
        return (Class<E>) typeToken.getRawType();
    }

    @SneakyThrows
    public <E> void test1(IService<E> service) {
        BaseEntity base = new BaseEntity();

        QueryWrapper<E> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("xx_id")
                .eq("PreAcceptanceNum", "1");

        E one = service.getOne(queryWrapper);
        if (one != null) {
            Field field = one.getClass()
                    .getField("xx_id");
            field.setAccessible(true);
            base.setDeleteFlag((Integer) field.get(one));
        }

        E e = service.getEntityClass()
                .getDeclaredConstructor()
                .newInstance();
        BeanUtils.copyProperties(base, e);
        service.save(e);
    }

    @SneakyThrows
    public <E extends BaseEntity> void test2(IService<E> service) {
        E e = service.getEntityClass()
                .getDeclaredConstructor()
                .newInstance();
    }

    public static void main(String[] args) {
        MybatisServiceTest mybatisServiceTest = new MybatisServiceTest();
        mybatisServiceTest.test2(new SysLogServiceImpl());
    }

    private <E extends BaseEntity> Class<? super E> getClass1(IService<E> service) {
        TypeToken<E> typeToken = new TypeToken<E>(getClass()) {
        };
        return typeToken.getRawType();
    }

}
