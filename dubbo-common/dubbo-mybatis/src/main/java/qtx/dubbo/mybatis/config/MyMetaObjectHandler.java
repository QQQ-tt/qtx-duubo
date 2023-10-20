package qtx.dubbo.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import qtx.dubbo.java.CommonMethod;

import java.time.LocalDateTime;

/**
 * @author qtx
 * @since 2022/8/30
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private final CommonMethod commonMethod;

    public MyMetaObjectHandler(CommonMethod commonMethod) {
        this.commonMethod = commonMethod;
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        String user = commonMethod.getUserCode();
        if (StringUtils.isBlank(user)) {
            user = commonMethod.getIp();
            if (StringUtils.isBlank(user)){
                user = "system";
            }
        }
        this.strictInsertFill(metaObject, "createOn", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "createBy", String.class, user);
        log.info("end insert fill ...");
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        String user = commonMethod.getUserCode();
        if (StringUtils.isBlank(user)) {
            user = commonMethod.getIp();
            if (StringUtils.isBlank(user)){
                user = "system";
            }
        }
        this.strictUpdateFill(metaObject, "updateOn", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "updateBy", String.class, user);
        log.info("end update fill ...");
    }

}

