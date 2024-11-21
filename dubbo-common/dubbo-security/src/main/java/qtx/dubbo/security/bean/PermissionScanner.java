package qtx.dubbo.security.bean;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qtx.dubbo.java.CommonMethod;
import qtx.dubbo.java.info.StaticConstant;
import qtx.dubbo.redis.util.RedisUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author qtx
 * @since 2024/11/21 0:32
 */
@Component
public class PermissionScanner {

    private final ApplicationContext applicationContext;
    private final RedisUtils redisUtils;

    public PermissionScanner(ApplicationContext applicationContext, RedisUtils redisUtils) {
        this.applicationContext = applicationContext;
        this.redisUtils = redisUtils;
    }

    @PostConstruct
    public void generatePermissionConfig() {
        // 获取springboot的名字
        String applicationName = applicationContext.getId();
        redisUtils.deleteByKey("permission:" + applicationName + ":*");
        List<Map<String, Object>> permissions = new ArrayList<>();
        List<String> publicPath = new ArrayList<>();

        // 获取所有 Controller Bean
        String[] beanNames = applicationContext.getBeanNamesForAnnotation(RestController.class);

        for (String beanName : beanNames) {
            Object controllerBean = applicationContext.getBean(beanName);
            Class<?> controllerClass = controllerBean.getClass();

            // 获取类上的 @Tag 注解
            Tag tagAnnotation = controllerClass.getAnnotation(Tag.class);
            // 模块名称
            String moduleName = (tagAnnotation != null) ? tagAnnotation.name() : "Default Module";

            // 遍历方法，获取接口相关信息
            for (Method method : controllerClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Operation.class)) {
                    // 获取 @Operation 信息
                    Operation operation = method.getAnnotation(Operation.class);
                    // 接口描述
                    String summary = operation.summary();
                    // 权限描述
                    String description = operation.description();
                    // 获取路径和方法信息
                    RequestMapping requestMapping = controllerClass.getAnnotation(RequestMapping.class);
                    // 路径
                    String fullPath = (requestMapping != null && requestMapping.value().length > 0) ? requestMapping.value()[0] : "";

                    // HTTP 方法
                    String httpMethod = "GET";

                    if (method.isAnnotationPresent(PostMapping.class)) {
                        fullPath += method.getAnnotation(PostMapping.class)
                                .value()[0];
                        httpMethod = "POST";
                    } else if (method.isAnnotationPresent(DeleteMapping.class)) {
                        fullPath += method.getAnnotation(DeleteMapping.class)
                                .value()[0];
                        httpMethod = "DELETE";
                    } else if (method.isAnnotationPresent(GetMapping.class)) {
                        fullPath += method.getAnnotation(GetMapping.class)
                                .value()[0];
                        httpMethod = "GET";
                    }

                    if (StaticConstant.AUTH_KEY.equals(description)) {
                        publicPath.add(fullPath);
                    }

                    // 构建权限配置
                    Map<String, Object> objectMap = Map.of("moduleName", moduleName, "path", fullPath, "httpMethod",
                            httpMethod,
                            "summary", summary, "public", description);
                    permissions.add(objectMap);
                    redisUtils.addListMsg("permission:" + applicationName + ":" + moduleName, objectMap);
                }
            }
        }
        CommonMethod.setAuthPublicPath(publicPath);
    }
}

