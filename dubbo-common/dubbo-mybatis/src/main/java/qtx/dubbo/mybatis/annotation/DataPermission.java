package qtx.dubbo.mybatis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author qtx
 * @since 2024/11/21 21:06
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DataPermission {

    String field() default "created_by"; // 指定过滤字段

    String[] table() default ""; // 表名

    int roleLevel() default 1; // 角色等级



}
