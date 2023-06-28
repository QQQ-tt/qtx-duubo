package qtx.dubbo.provider;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.*;

import org.apache.ibatis.annotations.Mapper;
import qtx.dubbo.model.base.BaseEntity;

/**
 * 代码生成器
 *
 * @author qtx
 * @since 2022/10/27 21:22
 */
public class Generator {

    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG =
            new DataSourceConfig.Builder("jdbc:mysql://172.16.6.77:3306/qtx_cloud", "root", "123456qaz");

    public static void main(String[] args) {
        String dubboName = "provider";
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
                                        .xml("mapper")
                                        .controller(dubboName + ".controller")
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
                .execute();
    }

    /**
     * 处理 all 情况
     */
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
