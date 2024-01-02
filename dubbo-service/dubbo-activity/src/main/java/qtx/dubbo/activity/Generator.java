package qtx.dubbo.activity;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import org.apache.ibatis.annotations.Mapper;
import qtx.dubbo.model.base.BaseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 *
 * @author qtx
 * @since 2022/10/27 21:22
 */
public class Generator {

    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG =
            new DataSourceConfig.Builder("jdbc:mysql://192.168.77.130:3306/cloud", "root", "123456qaz");

    public static void main(String[] args) {
        String dubboName = "activity";
        String path = "/dubbo-service/dubbo-" + dubboName;
        String projectPath = System.getProperty("user.dir");

        Map<OutputFile, String> map = new HashMap<>(5);
        map.put(OutputFile.xml, projectPath + path + "/src/main/resources" + "/mapper");
        map.put(
                OutputFile.entity,
                projectPath + "/dubbo-model" + "/src/main/java" + "/qtx/dubbo/model/entity/" + dubboName);
        map.put(
                OutputFile.service,
                projectPath + "/dubbo-interface" + "/src/main/java" + "/qtx/dubbo/service/" + dubboName);
        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                // 全局配置
                .globalConfig(
                        (scanner, builder) ->
                                builder
                                        .author("qtx")
                                        .outputDir(projectPath + path + "/src/main/java")
                                        .disableOpenDir())
                // 包配置
                .packageConfig(
                        (scanner, builder) ->
                                builder
                                        .parent("qtx.dubbo")
                                        .entity("model.entity." + dubboName)
                                        .service("service." + dubboName)
                                        .serviceImpl(dubboName + ".impl")
                                        .mapper(dubboName + ".mapper")
                                        .controller(dubboName + ".controller")
                                        .xml("mapper")
                                        .pathInfo(map))
                // 策略配置
                .strategyConfig(
                        (scanner, builder) ->
                                builder
                                        .addInclude(
                                                getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
                                        .controllerBuilder()
                                        .enableRestStyle()
                                        .serviceBuilder()
                                        .formatServiceFileName("%sService")
                                        .entityBuilder()
                                        .enableLombok()
                                        .superClass(BaseEntity.class)
                                        .enableTableFieldAnnotation()
                                        .enableRemoveIsPrefix()
                                        .addSuperEntityColumns(
                                                "delete_flag", "create_by", "create_on", "update_by", "update_on")
                                        .mapperBuilder()
                                        .mapperAnnotation(Mapper.class)
                                        .build())
//                .templateEngine(new FreemarkerTemplateEngine())
//                .templateConfig(builder -> builder.disable(TemplateType.CONTROLLER))
                .execute();
    }

    /**
     * 处理 all 情况
     */
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
